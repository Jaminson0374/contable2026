package co.posinvent.application.usecase;

import co.posinvent.application.dto.WarehouseLocationRequest;
import co.posinvent.application.dto.WarehouseLocationResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.WarehouseLocation;
import co.posinvent.domain.repository.WarehouseLocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WarehouseLocationUseCase {

    private final WarehouseLocationRepository repository;

    public WarehouseLocationUseCase(WarehouseLocationRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<WarehouseLocationResponse> listByWarehouse(UUID warehouseId) {
        return repository.findByWarehouseId(warehouseId).stream().map(WarehouseLocationResponse::from).toList();
    }

    @Transactional
    public WarehouseLocationResponse create(WarehouseLocationRequest request) {
        if (repository.existsByName(request.name())) {
            throw new BusinessException("DUPLICATE_NAME", "Ya existe una ubicación con ese nombre.");
        }
        var entity = new WarehouseLocation(null, request.warehouseId(), request.name(), request.description(), true, null);
        return WarehouseLocationResponse.from(repository.save(entity));
    }
}
