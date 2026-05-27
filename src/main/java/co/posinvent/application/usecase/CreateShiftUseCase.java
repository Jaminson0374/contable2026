package co.posinvent.application.usecase;

import co.posinvent.domain.model.Shift;
import co.posinvent.domain.model.ShiftStatus;
import co.posinvent.domain.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CreateShiftUseCase {

    private final ShiftRepository shiftRepository;

    @Autowired
    public CreateShiftUseCase(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Transactional
    public ShiftResponse execute(ShiftRequest request, UUID userId) {
        // Validate: no existing OPEN shift for same cash register
        var existing = shiftRepository.findByCashRegisterIdAndStatus(request.cashRegisterId(), ShiftStatus.OPEN);
        if (existing.isPresent()) {
            throw new IllegalArgumentException(
                    "Ya existe un turno ABIERTO para la caja " + request.cashRegisterId());
        }

        var openingAmount = request.openingAmount() != null ? request.openingAmount() : BigDecimal.ZERO;

        var shift = shiftRepository.save(new Shift(
                null,
                request.cashRegisterId(),
                userId,
                null,
                null,
                openingAmount,
                null,
                ShiftStatus.OPEN,
                null,
                null
        ));

        return ShiftResponse.from(shift);
    }
}
