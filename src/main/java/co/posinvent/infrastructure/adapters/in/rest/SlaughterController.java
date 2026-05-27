package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.SlaughterRequest;
import co.posinvent.application.dto.SlaughterResponse;
import co.posinvent.application.usecase.ProcessSlaughterUseCase;
import co.posinvent.infrastructure.adapters.out.security.PosUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/slaughters")
public class SlaughterController {

    private final ProcessSlaughterUseCase processSlaughterUseCase;

    public SlaughterController(ProcessSlaughterUseCase processSlaughterUseCase) {
        this.processSlaughterUseCase = processSlaughterUseCase;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CARNICERO')")
    public ResponseEntity<SlaughterResponse> process(
            @Valid @RequestBody SlaughterRequest request,
            @AuthenticationPrincipal PosUserDetails principal
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(processSlaughterUseCase.process(request, principal.userId()));
    }
}
