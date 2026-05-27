package co.posinvent.application.dto;

import co.posinvent.domain.model.Collection.CollectionStatus;

public record LogContactRequest(
        String contactMethod,
        String contactNotes,
        CollectionStatus newStatus
) {}
