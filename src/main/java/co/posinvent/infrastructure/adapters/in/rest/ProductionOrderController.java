package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ProductionOrderRequest;
import co.posinvent.application.dto.ProductionOrderResponse;
import co.posinvent.application.usecase.ManageProductionOrderUseCase;
import co.posinvent.domain.model.ProductionOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/production-orders")
public class ProductionOrderController {

    private final ManageProductionOrderUseCase useCase;

    public ProductionOrderController(ManageProductionOrderUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CARNICERO')")
    public ResponseEntity<ProductionOrderResponse> create(@RequestBody ProductionOrderRequest request) {
        return ResponseEntity.ok(useCase.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CARNICERO','ALMACENISTA')")
    public ResponseEntity<Page<ProductionOrderResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) ProductionOrderStatus status,
            @RequestParam(required = false) UUID warehouseId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(useCase.list(status, warehouseId, from, to, page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CARNICERO','ALMACENISTA')")
    public ResponseEntity<ProductionOrderResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN','CONTADOR')")
    public ResponseEntity<ProductionOrderResponse> approve(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.approve(id));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','CARNICERO')")
    public ResponseEntity<ProductionOrderResponse> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.cancel(id));
    }
}
