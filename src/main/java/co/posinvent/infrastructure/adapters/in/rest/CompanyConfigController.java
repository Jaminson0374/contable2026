package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.CompanyConfigRequest;
import co.posinvent.application.dto.CompanyConfigResponse;
import co.posinvent.application.usecase.CompanyConfigUseCase;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/company-config")
@PreAuthorize("hasRole('ADMIN')")
public class CompanyConfigController {

    private final CompanyConfigUseCase useCase;

    public CompanyConfigController(CompanyConfigUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public CompanyConfigResponse getConfig() {
        return useCase.getConfig();
    }

    @PutMapping
    public CompanyConfigResponse saveConfig(@Valid @RequestBody CompanyConfigRequest request) {
        return useCase.saveConfig(request);
    }
}
