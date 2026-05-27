package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.CustomPriceRequest;
import co.posinvent.application.dto.CustomPriceResponse;
import co.posinvent.application.usecase.CustomPriceUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/custom-prices")
@PreAuthorize("hasRole('ADMIN')")
public class CustomPriceController {

    private final CustomPriceUseCase useCase;

    public CustomPriceController(CustomPriceUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<CustomPriceResponse> listAll(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) UUID productId
    ) {
        return useCase.listAll(clientId, productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomPriceResponse create(@Valid @RequestBody CustomPriceRequest request) {
        return useCase.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomPriceResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody CustomPriceRequest request
    ) {
        return ResponseEntity.ok(useCase.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}