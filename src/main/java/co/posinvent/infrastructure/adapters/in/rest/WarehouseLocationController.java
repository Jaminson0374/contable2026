package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.WarehouseLocationRequest;
import co.posinvent.application.dto.WarehouseLocationResponse;
import co.posinvent.application.usecase.WarehouseLocationUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouse-locations")
public class WarehouseLocationController {

    private final WarehouseLocationUseCase useCase;

    public WarehouseLocationController(WarehouseLocationUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<WarehouseLocationResponse> listAll(@RequestParam UUID warehouseId) {
        return useCase.listByWarehouse(warehouseId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public WarehouseLocationResponse create(@Valid @RequestBody WarehouseLocationRequest request) {
        return useCase.create(request);
    }
}
