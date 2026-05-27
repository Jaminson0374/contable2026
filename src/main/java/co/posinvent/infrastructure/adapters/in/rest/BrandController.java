package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.BrandRequest;
import co.posinvent.application.dto.BrandResponse;
import co.posinvent.application.usecase.BrandUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandUseCase useCase;

    public BrandController(BrandUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<BrandResponse> listAll() {
        return useCase.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public BrandResponse create(@Valid @RequestBody BrandRequest request) {
        return useCase.create(request);
    }
}
