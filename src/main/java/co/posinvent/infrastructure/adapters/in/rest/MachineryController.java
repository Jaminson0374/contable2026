package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.domain.model.Machinery;
import co.posinvent.application.usecase.ManageMachineryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/machinery")
public class MachineryController {
    private final ManageMachineryUseCase useCase;
    public MachineryController(ManageMachineryUseCase u) { this.useCase = u; }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CARNICERO','ALMACENISTA')")
    public ResponseEntity<List<Machinery>> list() { return ResponseEntity.ok(useCase.list()); }

    @GetMapping("/{id}")
    public ResponseEntity<Machinery> getById(@PathVariable UUID id) { return ResponseEntity.ok(useCase.getById(id)); }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Machinery> create(@RequestBody Map<String, String> body) { return ResponseEntity.ok(useCase.create(body.get("code"), body.get("name"), body.get("machineryType"))); }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Machinery> update(@PathVariable UUID id, @RequestBody Map<String, String> body) { return ResponseEntity.ok(useCase.update(id, body.get("name"), body.get("machineryType"), body.get("status"))); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) { useCase.deactivate(id); return ResponseEntity.noContent().build(); }
}
