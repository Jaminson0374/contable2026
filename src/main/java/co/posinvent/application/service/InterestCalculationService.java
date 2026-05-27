package co.posinvent.application.service;

import co.posinvent.application.dto.InterestCalculationResponse;
import co.posinvent.domain.model.AccountsReceivable;
import co.posinvent.domain.repository.AccountsReceivableRepository;
import co.posinvent.domain.repository.CompanyConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
public class InterestCalculationService {

    private final AccountsReceivableRepository arRepo;
    private final CompanyConfigRepository configRepo;

    public InterestCalculationService(
            AccountsReceivableRepository arRepo,
            CompanyConfigRepository configRepo
    ) {
        this.arRepo = arRepo;
        this.configRepo = configRepo;
    }

    @Transactional
    public InterestCalculationResponse calculateAllOverdueInterest() {
        var today = LocalDate.now();
        var errors = new ArrayList<String>();
        var totalInterest = BigDecimal.ZERO.setScale(2);
        int processedCount = 0;

        // 1. Load company config
        var configOpt = configRepo.findConfig();
        if (configOpt.isEmpty()) {
            errors.add("No se encontró la configuración de empresa.");
            return new InterestCalculationResponse(0, BigDecimal.ZERO, errors);
        }
        var config = configOpt.get();

        var moratoryRate = config.moratoryInterestRate();
        if (moratoryRate == null || moratoryRate.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("No se ha configurado la tasa de interés moratorio.");
            return new InterestCalculationResponse(0, BigDecimal.ZERO, errors);
        }

        var graceDays = config.interestGraceDays() != null ? config.interestGraceDays() : 0;
        var cutoffDate = today.minusDays(graceDays);

        // 2. Find all AR with status=OVERDUE, outstanding > 0, and dueDate + graceDays < today
        var overdueArs = arRepo.findByStatusAndOutstandingGreaterThan(
                AccountsReceivable.ArStatus.OVERDUE,
                BigDecimal.ZERO,
                cutoffDate
        );

        for (var ar : overdueArs) {
            try {
                // 3. Same-day guard
                if (ar.lastInterestCalcDate() != null && ar.lastInterestCalcDate().equals(today)) {
                    continue;
                }

                // 4. Interest rate: AR.interest_rate ?? config.moratory_rate
                var effectiveRate = ar.interestRate() != null ? ar.interestRate() : moratoryRate;

                // 5. Calculate months overdue based on due date
                var months = ChronoUnit.MONTHS.between(ar.dueDate(), today);
                if (months <= 0) {
                    months = 1;
                }

                BigDecimal calculated;
                var compoundFrequency = config.interestCompoundFrequency();
                if ("MONTHLY".equalsIgnoreCase(compoundFrequency)) {
                    // Monthly compound: outstanding * ((1 + rate/1200)^months - 1)
                    var monthlyRate = effectiveRate.divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
                    var compound = BigDecimal.ONE.add(monthlyRate).pow((int) months);
                    calculated = ar.outstanding().multiply(compound.subtract(BigDecimal.ONE))
                            .setScale(2, RoundingMode.HALF_UP);
                } else {
                    // Simple monthly: (outstanding * rate/100) / 12 * months
                    var monthlyRate = effectiveRate.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                            .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
                    calculated = ar.outstanding().multiply(monthlyRate)
                            .multiply(BigDecimal.valueOf(months))
                            .setScale(2, RoundingMode.HALF_UP);
                }

                if (calculated.compareTo(BigDecimal.ZERO) <= 0) continue;

                // 7. Update AR
                var newInterestAmount = ar.interestAmount().add(calculated);
                var updated = new AccountsReceivable(
                        ar.id(), ar.clientId(), ar.documentId(),
                        ar.totalAmount(), ar.paidAmount(), ar.outstanding(),
                        ar.dueDate(), ar.status(),
                        ar.createdAt(), null,
                        ar.interestRate(), newInterestAmount, today
                );
                arRepo.save(updated);

                totalInterest = totalInterest.add(calculated);
                processedCount++;
            } catch (Exception e) {
                errors.add("Error procesando AR " + ar.id() + ": " + e.getMessage());
            }
        }

        return new InterestCalculationResponse(processedCount, totalInterest, errors);
    }
}
