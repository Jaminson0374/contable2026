package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ProductStateRequest;
import co.posinvent.application.dto.ProductStateResponse;
import co.posinvent.application.usecase.ProductStateUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product-states")
public class ProductStateController {

    private final ProductStateUseCase useCase;

    public ProductStateController(ProductStateUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<ProductStateResponse> listAll() {
        return useCase.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductStateResponse create(@Valid @RequestBody ProductStateRequest request) {
        return useCase.create(request);
    }
}
