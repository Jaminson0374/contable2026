package co.posinvent.application.usecase;

import co.posinvent.domain.model.Machinery;
import co.posinvent.domain.repository.MachineryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ManageMachineryUseCase {
    private final MachineryRepository repo;
    public ManageMachineryUseCase(MachineryRepository r) { this.repo = r; }

    @Transactional public Machinery create(String code, String name, String type) { return repo.save(new Machinery(null, code, name, type, "OPERATIONAL", null)); }
    @Transactional public Machinery update(UUID id, String name, String type, String status) { var m = repo.findById(id).orElseThrow(); return repo.save(new Machinery(m.id(), m.code(), name, type, status, m.createdAt())); }
    @Transactional public void deactivate(UUID id) { var m = repo.findById(id).orElseThrow(); repo.save(new Machinery(m.id(), m.code(), m.name(), m.machineryType(), "DECOMMISSIONED", m.createdAt())); }
    @Transactional(readOnly = true) public List<Machinery> list() { return repo.findAll(); }
    @Transactional(readOnly = true) public Machinery getById(UUID id) { return repo.findById(id).orElseThrow(); }
}
