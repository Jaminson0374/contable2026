package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ProductTypeRequest;
import co.posinvent.application.dto.ProductTypeResponse;
import co.posinvent.application.usecase.ProductTypeUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product-types")
public class ProductTypeController {

    private final ProductTypeUseCase useCase;

    public ProductTypeController(ProductTypeUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<ProductTypeResponse> listAll() {
        return useCase.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductTypeResponse create(@Valid @RequestBody ProductTypeRequest request) {
        return useCase.create(request);
    }
}
