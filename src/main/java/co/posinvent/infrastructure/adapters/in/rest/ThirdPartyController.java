package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.PageResponse;
import co.posinvent.application.dto.ThirdPartyRequest;
import co.posinvent.application.dto.ThirdPartyResponse;
import co.posinvent.application.dto.ThirdPartySupplierOptionResponse;
import co.posinvent.application.usecase.ThirdPartyUseCase;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/third-parties")
public class ThirdPartyController {

    private final ThirdPartyUseCase thirdPartyUseCase;

    public ThirdPartyController(ThirdPartyUseCase thirdPartyUseCase) {
        this.thirdPartyUseCase = thirdPartyUseCase;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAJERO','CONTADOR')")
    public ResponseEntity<PageResponse<ThirdPartyResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String q
    ) {
        var pageable = PageRequest.of(page, size, Sort.by("name"));
        var result = (q != null && !q.isBlank())
                ? thirdPartyUseCase.search(q, pageable)
                : thirdPartyUseCase.list(pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/suppliers")
    @PreAuthorize("hasAnyRole('ADMIN','CARNICERO','AUXILIAR','CAJERO','CONTADOR')")
    public ResponseEntity<List<ThirdPartySupplierOptionResponse>> listSuppliers() {
        return ResponseEntity.ok(thirdPartyUseCase.listSupplierOptions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAJERO','CONTADOR')")
    public ResponseEntity<ThirdPartyResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(thirdPartyUseCase.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ThirdPartyResponse> create(@Valid @RequestBody ThirdPartyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(thirdPartyUseCase.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ThirdPartyResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ThirdPartyRequest request
    ) {
        return ResponseEntity.ok(thirdPartyUseCase.update(id, request));
    }
}
