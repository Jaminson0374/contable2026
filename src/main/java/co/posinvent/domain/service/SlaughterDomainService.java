package co.posinvent.domain.service;

import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.Animal;
import co.posinvent.domain.model.Animal.AnimalStatus;
import co.posinvent.domain.model.Slaughter.SlaughterSourceType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Validación pura de dominio para el proceso de faena.
 */
@Service
public final class SlaughterDomainService {

    private static final int SCALE = 6;

    /**
     * Valida que el animal esté en condiciones de ser procesado y que los datos
     * de faena sean consistentes.
     *
     * @return el porcentaje de rendimiento calculado (carcassWeight / liveWeight * 100)
     * @throws BusinessException si alguna validación falla
     */
    public BigDecimal validate(
            Animal animal,
            BigDecimal carcassWeight,
            BigDecimal purchaseCost,
            SlaughterSourceType sourceType,
            String justification
    ) {
        ensureProcessableAnimal(animal);
        validateSourceType(sourceType, justification);
        validateCarcassWeight(carcassWeight, animal.liveWeight());
        requirePositive(purchaseCost, "El costo de compra debe ser mayor a cero");

        return carcassWeight
                .divide(animal.liveWeight(), SCALE, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private void ensureProcessableAnimal(Animal animal) {
        if (animal.status() == AnimalStatus.SLAUGHTERED) {
            throw new BusinessException(
                    "ANIMAL_ALREADY_SLAUGHTERED",
                    "El animal con lote ICA " + animal.icaLotNumber() + " ya fue sacrificado"
            );
        }
        // IN_SLAUGHTER también se rechaza — solo RECEIVED es procesable.
        // Nota: IN_SLAUGHTER se podría usar en el futuro para un flujo con pasos intermedios.
    }

    private void validateSourceType(SlaughterSourceType sourceType, String justification) {
        if (sourceType == SlaughterSourceType.AUTOMATIC) {
            throw new BusinessException(
                    "AUTOMATIC_SLAUGHTER_NOT_SUPPORTED",
                    "El sacrificio automático (Web Serial API) estará disponible próximamente. " +
                    "Por ahora usá MANUAL con la justificación correspondiente."
            );
        }

        if (justification == null || justification.trim().isEmpty()) {
            throw new BusinessException(
                    "MANUAL_JUSTIFICATION_REQUIRED",
                    "La justificación es obligatoria para el sacrificio manual"
            );
        }
    }

    private void validateCarcassWeight(BigDecimal carcassWeight, BigDecimal liveWeight) {
        requirePositive(carcassWeight, "El peso en canal debe ser mayor a cero");

        if (carcassWeight.compareTo(liveWeight) > 0) {
            throw new BusinessException(
                    "CARCASS_EXCEEDS_LIVE_WEIGHT",
                    "El peso en canal (" + carcassWeight + " kg) no puede superar " +
                    "el peso vivo (" + liveWeight + " kg)"
            );
        }
    }

    private void requirePositive(BigDecimal value, String message) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("INVALID_SLAUGHTER_VALUE", message);
        }
    }
}
