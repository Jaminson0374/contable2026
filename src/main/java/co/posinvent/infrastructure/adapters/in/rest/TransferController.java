package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.TransferRequest;
import co.posinvent.application.dto.TransferResponse;
import co.posinvent.application.usecase.CancelTransferUseCase;
import co.posinvent.application.usecase.ConfirmTransferUseCase;
import co.posinvent.application.usecase.CreateTransferUseCase;
import co.posinvent.domain.exception.ResourceNotFoundException;
import co.posinvent.domain.repository.StockTransferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final CreateTransferUseCase createTransfer;
    private final ConfirmTransferUseCase confirmTransfer;
    private final CancelTransferUseCase cancelTransfer;
    private final StockTransferRepository transferRepo;

    public TransferController(
            CreateTransferUseCase createTransfer, ConfirmTransferUseCase confirmTransfer,
            CancelTransferUseCase cancelTransfer, StockTransferRepository transferRepo
    ) {
        this.createTransfer = createTransfer;
        this.confirmTransfer = confirmTransfer;
        this.cancelTransfer = cancelTransfer;
        this.transferRepo = transferRepo;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ALMACENISTA')")
    public ResponseEntity<TransferResponse> create(@RequestBody TransferRequest request) {
        return ResponseEntity.ok(createTransfer.execute(request));
    }

    @PostMapping("/{id}/confirm")
    @PreAuthorize("hasAnyRole('ADMIN','ALMACENISTA')")
    public ResponseEntity<TransferResponse> confirm(@PathVariable UUID id) {
        return ResponseEntity.ok(confirmTransfer.execute(id));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','ALMACENISTA')")
    public ResponseEntity<TransferResponse> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(cancelTransfer.execute(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ALMACENISTA','CAJERO')")
    public ResponseEntity<Page<TransferResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(transferRepo.findAll(PageRequest.of(page, size)).map(TransferResponse::from));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ALMACENISTA','CAJERO')")
    public ResponseEntity<TransferResponse> getById(@PathVariable UUID id) {
        return transferRepo.findById(id)
                .map(TransferResponse::from)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Traslado", id));
    }
}
