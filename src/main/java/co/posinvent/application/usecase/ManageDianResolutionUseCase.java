package co.posinvent.application.usecase;

import co.posinvent.application.dto.DianResolutionRequest;
import co.posinvent.application.dto.DianResolutionResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.exception.ResourceNotFoundException;
import co.posinvent.domain.model.DianResolution;
import co.posinvent.domain.repository.DianResolutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ManageDianResolutionUseCase {

    private final DianResolutionRepository repository;

    public ManageDianResolutionUseCase(DianResolutionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<DianResolutionResponse> listAll() {
        return repository.findAll().stream()
                .map(DianResolutionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public DianResolutionResponse getById(UUID id) {
        return repository.findById(id)
                .map(DianResolutionResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Resolución DIAN", id));
    }

    @Transactional
    public DianResolutionResponse create(DianResolutionRequest request) {
        validateNoOverlap(request.validFrom(), request.validTo(), null);

        var resolution = new DianResolution(
                null,
                request.resolutionNumber(),
                request.resolutionDate(),
                request.validFrom(),
                request.validTo(),
                request.prefix() != null ? request.prefix() : "",
                request.rangeFrom(),
                request.rangeTo(),
                request.softwarePin(),
                request.active() != null ? request.active() : false,
                null
        );

        var saved = repository.save(resolution);
        return DianResolutionResponse.from(saved);
    }

    @Transactional
    public DianResolutionResponse update(UUID id, DianResolutionRequest request) {
        var existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resolución DIAN", id));

        validateNoOverlap(request.validFrom(), request.validTo(), id);

        var updated = new DianResolution(
                id,
                request.resolutionNumber(),
                request.resolutionDate(),
                request.validFrom(),
                request.validTo(),
                request.prefix() != null ? request.prefix() : "",
                request.rangeFrom(),
                request.rangeTo(),
                request.softwarePin(),
                request.active() != null ? request.active() : existing.active(),
                null
        );

        var saved = repository.save(updated);
        return DianResolutionResponse.from(saved);
    }

    @Transactional
    public DianResolutionResponse activate(UUID id) {
        var resolution = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resolución DIAN", id));

        repository.deactivateAll();

        var activated = new DianResolution(
                id,
                resolution.resolutionNumber(),
                resolution.resolutionDate(),
                resolution.validFrom(),
                resolution.validTo(),
                resolution.prefix(),
                resolution.rangeFrom(),
                resolution.rangeTo(),
                resolution.softwarePin(),
                true,
                null
        );

        var saved = repository.save(activated);
        return DianResolutionResponse.from(saved);
    }

    @Transactional
    public void delete(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Resolución DIAN", id);
        }
        repository.deleteById(id);
    }

    private void validateNoOverlap(java.time.LocalDate from, java.time.LocalDate to, UUID excludeId) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new BusinessException("DIAN_DATE_RANGE",
                    "La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
        }
    }
}
