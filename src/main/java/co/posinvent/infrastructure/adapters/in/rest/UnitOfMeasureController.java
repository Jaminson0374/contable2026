package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.UnitOfMeasureRequest;
import co.posinvent.application.dto.UnitOfMeasureResponse;
import co.posinvent.application.usecase.UnitOfMeasureUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/units-of-measure")
public class UnitOfMeasureController {

    private final UnitOfMeasureUseCase useCase;

    public UnitOfMeasureController(UnitOfMeasureUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<UnitOfMeasureResponse> listAll() {
        return useCase.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public UnitOfMeasureResponse create(@Valid @RequestBody UnitOfMeasureRequest request) {
        return useCase.create(request);
    }
}
