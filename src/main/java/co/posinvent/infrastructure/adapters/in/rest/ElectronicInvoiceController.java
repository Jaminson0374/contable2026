package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ElectronicInvoiceResponse;
import co.posinvent.application.usecase.ManageElectronicInvoiceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/electronic-invoices")
@PreAuthorize("hasRole('ADMIN')")
public class ElectronicInvoiceController {

    private final ManageElectronicInvoiceUseCase useCase;

    public ElectronicInvoiceController(ManageElectronicInvoiceUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<ElectronicInvoiceResponse> list(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return useCase.list(status, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectronicInvoiceResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @GetMapping("/by-document/{salesDocumentId}")
    public ResponseEntity<ElectronicInvoiceResponse> getBySalesDocument(
            @PathVariable UUID salesDocumentId) {
        return ResponseEntity.ok(useCase.getBySalesDocument(salesDocumentId));
    }

    @PostMapping("/{id}/retry")
    public ResponseEntity<String> retry(@PathVariable UUID id) {
        useCase.retry(id);
        return ResponseEntity.ok("Retry scheduled");
    }
}
