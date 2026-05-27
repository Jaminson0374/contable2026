package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.ManualDesposteRequest;
import co.posinvent.application.dto.ManualDesposteResponse;
import co.posinvent.application.usecase.ManualDesposteUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/despostes")
public class DesposteController {

    private final ManualDesposteUseCase manualDesposteUseCase;

    public DesposteController(ManualDesposteUseCase manualDesposteUseCase) {
        this.manualDesposteUseCase = manualDesposteUseCase;
    }

    @PostMapping("/manual")
    @PreAuthorize("hasAnyRole('ADMIN','CARNICERO')")
    public ResponseEntity<ManualDesposteResponse> processManual(@Valid @RequestBody ManualDesposteRequest request) {
        return ResponseEntity.ok(manualDesposteUseCase.processManual(request));
    }
}
