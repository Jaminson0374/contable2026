package co.posinvent.application.service;

import co.posinvent.domain.exception.ResourceNotFoundException;
import co.posinvent.domain.model.ThirdParty;
import co.posinvent.domain.repository.ThirdPartyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Shared service for updating supplier currentBalance.
 *
 * Sign convention: positive currentBalance = we owe the supplier (debt).
 * An advance or payment reduces debt (subtracts from currentBalance).
 */
@Service
public class SupplierBalanceService {

    private final ThirdPartyRepository thirdPartyRepository;

    public SupplierBalanceService(ThirdPartyRepository thirdPartyRepository) {
        this.thirdPartyRepository = thirdPartyRepository;
    }

    /**
     * Updates a supplier's currentBalance.
     *
     * @param supplierId the supplier to update
     * @param amount     the amount to apply (must be positive)
     * @param isCredit   true = reduces debt (subtracts), false = increases debt (adds)
     */
    @Transactional
    public void updateSupplierBalance(UUID supplierId, BigDecimal amount, boolean isCredit) {
        var supplier = thirdPartyRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor", supplierId));

        var delta = isCredit ? amount.negate() : amount;

        var newBalance = supplier.currentBalance() != null
                ? supplier.currentBalance().add(delta)
                : delta;

        var updated = new ThirdParty(
                supplier.id(),
                supplier.numIdentification(),
                supplier.name(),
                supplier.type(),
                supplier.priceListId(),
                supplier.creditLimit(),
                newBalance,
                supplier.personType(),
                supplier.taxRegime(),
                supplier.taxResponsibilities(),
                supplier.cityCode(),
                supplier.dianClassification(),
                supplier.active(),
                supplier.createdAt(),
                null,
                supplier.thirdPartyCategoryId(),
                supplier.identificationTypeId(),
                supplier.dv(),
                supplier.lastName(),
                supplier.commonName(),
                supplier.phone(),
                supplier.address(),
                supplier.departmentId(),
                supplier.cityId(),
                supplier.email(),
                supplier.website(),
                supplier.entryDate(),
                supplier.creditDays(),
                supplier.contactName(),
                supplier.contactPhone(),
                supplier.contactAddress(),
                supplier.contactEmail(),
                supplier.taxContactFirstName(),
                supplier.taxContactLastName(),
                supplier.taxEmail(),
                supplier.billingPhone(),
                supplier.isGranContribuyente(),
                supplier.isAutoretenedor(),
                supplier.isAgenteRetencionIva(),
                supplier.isRegimenSimple(),
                supplier.otherTaxResp(),
                supplier.employeeData()
        );

        thirdPartyRepository.save(updated);
    }
}
