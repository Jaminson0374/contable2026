package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ProductPresentationRequest;
import co.posinvent.application.dto.ProductPresentationResponse;
import co.posinvent.application.usecase.ManageProductPresentationsUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products/{productId}/presentations")
public class ProductPresentationController {

    private final ManageProductPresentationsUseCase useCase;

    public ProductPresentationController(ManageProductPresentationsUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public ResponseEntity<List<ProductPresentationResponse>> list(@PathVariable UUID productId) {
        return ResponseEntity.ok(useCase.listByProduct(productId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductPresentationResponse> create(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductPresentationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(productId, request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductPresentationResponse> update(
            @PathVariable UUID productId,
            @PathVariable UUID id,
            @Valid @RequestBody ProductPresentationRequest request) {
        return ResponseEntity.ok(useCase.update(productId, id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID productId, @PathVariable UUID id) {
        useCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
