package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ProductModelRequest;
import co.posinvent.application.dto.ProductModelResponse;
import co.posinvent.application.usecase.ProductModelUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product-models")
public class ProductModelController {

    private final ProductModelUseCase useCase;

    public ProductModelController(ProductModelUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<ProductModelResponse> listAll(@RequestParam(required = false) UUID brandId) {
        return brandId != null ? useCase.listByBrand(brandId) : useCase.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductModelResponse create(@Valid @RequestBody ProductModelRequest request) {
        return useCase.create(request);
    }
}
