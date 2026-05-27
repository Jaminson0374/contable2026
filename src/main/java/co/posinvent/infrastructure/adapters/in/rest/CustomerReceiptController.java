package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.CustomerReceiptRequest;
import co.posinvent.application.dto.CustomerReceiptResponse;
import co.posinvent.application.dto.PageResponse;
import co.posinvent.application.usecase.CustomerReceiptUseCase;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer-receipts")
public class CustomerReceiptController {

    private final CustomerReceiptUseCase useCase;

    public CustomerReceiptController(CustomerReceiptUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAJERO','CONTADOR')")
    public ResponseEntity<CustomerReceiptResponse> create(
            @Valid @RequestBody CustomerReceiptRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAJERO','CONTADOR')")
    public ResponseEntity<PageResponse<CustomerReceiptResponse>> list(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "paymentDate"));
        PageResponse<CustomerReceiptResponse> result;

        if (clientId != null) {
            result = useCase.listByClient(clientId, pageable);
        } else {
            result = useCase.listAll(pageable);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CAJERO','CONTADOR')")
    public ResponseEntity<CustomerReceiptResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }
}
