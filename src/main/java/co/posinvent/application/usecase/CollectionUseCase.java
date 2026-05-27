package co.posinvent.application.usecase;

import co.posinvent.application.dto.CollectionResponse;
import co.posinvent.application.dto.LogContactRequest;
import co.posinvent.domain.exception.ResourceNotFoundException;
import co.posinvent.domain.model.Collection;
import co.posinvent.domain.model.Collection.CollectionStatus;
import co.posinvent.domain.repository.AccountsReceivableRepository;
import co.posinvent.domain.repository.CollectionRepository;
import co.posinvent.domain.repository.SalesDocumentRepository;
import co.posinvent.domain.repository.ThirdPartyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CollectionUseCase {

    private final CollectionRepository colRepo;
    private final ThirdPartyRepository thirdPartyRepo;
    private final AccountsReceivableRepository arRepo;
    private final SalesDocumentRepository documentRepo;

    public CollectionUseCase(
            CollectionRepository colRepo,
            ThirdPartyRepository thirdPartyRepo,
            AccountsReceivableRepository arRepo,
            SalesDocumentRepository documentRepo
    ) {
        this.colRepo = colRepo;
        this.thirdPartyRepo = thirdPartyRepo;
        this.arRepo = arRepo;
        this.documentRepo = documentRepo;
    }

    public Page<CollectionResponse> list(Pageable pageable, String clientId, String status) {
        Page<Collection> page;
        if (clientId != null && !clientId.isBlank() && status != null && !status.isBlank()) {
            page = colRepo.findByClientIdAndStatus(UUID.fromString(clientId), CollectionStatus.valueOf(status), pageable);
        } else if (clientId != null && !clientId.isBlank()) {
            page = colRepo.findByClientId(UUID.fromString(clientId), pageable);
        } else if (status != null && !status.isBlank()) {
            page = colRepo.findByStatus(CollectionStatus.valueOf(status), pageable);
        } else {
            page = colRepo.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    public Page<CollectionResponse> listOverdue(Pageable pageable) {
        var statuses = new CollectionStatus[]{CollectionStatus.PENDING, CollectionStatus.CONTACTED, CollectionStatus.PROMISED};
        // Filter overdue: status in active states and due_date < today
        // For simplicity, list by status PENDING first since those are most likely overdue
        var pageableSorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "dueDate"));
        var page = colRepo.findByStatus(CollectionStatus.PENDING, pageableSorted);
        return page.map(this::toResponse);
    }

    @Transactional
    public CollectionResponse logContact(UUID id, LogContactRequest request) {
        var existing = colRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cobranza", id));

        var updated = new Collection(
                existing.id(),
                existing.clientId(),
                existing.arId(),
                existing.dueDate(),
                request.newStatus() != null ? request.newStatus() : existing.status(),
                LocalDate.now(),
                request.contactMethod(),
                request.contactNotes(),
                existing.assignedTo(),
                existing.createdAt(),
                existing.updatedAt()
        );

        return toResponse(colRepo.save(updated));
    }

    private CollectionResponse toResponse(Collection c) {
        var client = thirdPartyRepo.findById(c.clientId()).orElse(null);
        var ar = arRepo.findById(c.arId()).orElse(null);
        var doc = ar != null ? documentRepo.findById(ar.documentId()).orElse(null) : null;
        return new CollectionResponse(
                c.id().toString(),
                c.clientId().toString(),
                client != null ? client.name() : null,
                c.arId().toString(),
                doc != null ? doc.documentNumber() : null,
                c.dueDate(),
                c.status(),
                c.lastContactDate(),
                c.contactMethod(),
                c.contactNotes(),
                c.assignedTo(),
                c.createdAt(),
                c.updatedAt()
        );
    }
}
