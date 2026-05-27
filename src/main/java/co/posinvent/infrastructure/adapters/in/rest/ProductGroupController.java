package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ProductGroupRequest;
import co.posinvent.application.dto.ProductGroupResponse;
import co.posinvent.application.usecase.ProductGroupUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product-groups")
public class ProductGroupController {

    private final ProductGroupUseCase useCase;

    public ProductGroupController(ProductGroupUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<ProductGroupResponse> listAll(@RequestParam(required = false) UUID categoryId) {
        return categoryId != null ? useCase.listByCategory(categoryId) : useCase.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductGroupResponse create(@Valid @RequestBody ProductGroupRequest request) {
        return useCase.create(request);
    }
}
