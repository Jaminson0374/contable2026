package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ThirdPartyCategoryRequest;
import co.posinvent.application.dto.ThirdPartyCategoryResponse;
import co.posinvent.application.usecase.ThirdPartyCategoryUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/third-party-categories")
public class ThirdPartyCategoryController {

    private final ThirdPartyCategoryUseCase thirdPartyCategoryUseCase;

    public ThirdPartyCategoryController(ThirdPartyCategoryUseCase thirdPartyCategoryUseCase) {
        this.thirdPartyCategoryUseCase = thirdPartyCategoryUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ThirdPartyCategoryResponse>> listActive() {
        return ResponseEntity.ok(thirdPartyCategoryUseCase.listActive());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ThirdPartyCategoryResponse> create(@Valid @RequestBody ThirdPartyCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(thirdPartyCategoryUseCase.create(request));
    }
}
