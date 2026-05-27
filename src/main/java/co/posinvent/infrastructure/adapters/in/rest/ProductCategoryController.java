package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ProductCategoryRequest;
import co.posinvent.application.dto.ProductCategoryResponse;
import co.posinvent.application.usecase.ProductCategoryUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product-categories")
public class ProductCategoryController {

    private final ProductCategoryUseCase useCase;

    public ProductCategoryController(ProductCategoryUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<ProductCategoryResponse> listAll() {
        return useCase.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductCategoryResponse create(@Valid @RequestBody ProductCategoryRequest request) {
        return useCase.create(request);
    }
}
