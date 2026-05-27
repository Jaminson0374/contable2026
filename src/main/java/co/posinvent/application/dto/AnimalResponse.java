package co.posinvent.application.dto;

import co.posinvent.domain.model.Animal;
import co.posinvent.domain.model.Animal.AnimalStatus;
import co.posinvent.domain.model.Animal.Species;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AnimalResponse(
        UUID id,
        String icaLotNumber,
        UUID supplierId,
        Species species,
        BigDecimal liveWeight,
        LocalDate receptionDate,
        AnimalStatus status,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt
) {
    public static AnimalResponse from(Animal a) {
        return new AnimalResponse(
                a.id(), a.icaLotNumber(), a.supplierId(), a.species(),
                a.liveWeight(), a.receptionDate(), a.status(),
                a.notes(), a.createdBy(), a.createdAt()
        );
    }
}
