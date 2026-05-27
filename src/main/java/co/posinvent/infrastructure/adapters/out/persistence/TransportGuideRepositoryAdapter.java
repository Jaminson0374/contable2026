package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.TransportGuide;
import co.posinvent.domain.repository.TransportGuideRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class TransportGuideRepositoryAdapter implements TransportGuideRepository {

    private final TransportGuideJpaRepository jpa;
    private final TransportGuideMapper mapper;

    public TransportGuideRepositoryAdapter(TransportGuideJpaRepository jpa, TransportGuideMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public TransportGuide save(TransportGuide guide) {
        return mapper.toDomain(jpa.save(mapper.toEntity(guide)));
    }

    @Override
    public Optional<TransportGuide> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<TransportGuide> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }
}
