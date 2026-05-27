package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.infrastructure.adapters.out.persistence.CashRegisterJpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cash-registers")
public class CashRegisterController {

    private final CashRegisterJpaRepository jpa;

    public CashRegisterController(CashRegisterJpaRepository jpa) {
        this.jpa = jpa;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CAJERO')")
    public List<CashRegisterResponse> listActive() {
        return jpa.findByActiveTrueOrderByName().stream()
                .map(e -> new CashRegisterResponse(e.getId(), e.getName(), e.getLocation()))
                .toList();
    }

    record CashRegisterResponse(UUID id, String name, String location) {}
}
