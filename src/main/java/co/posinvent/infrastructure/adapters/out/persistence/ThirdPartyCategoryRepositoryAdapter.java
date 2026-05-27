package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ThirdPartyCategory;
import co.posinvent.domain.repository.ThirdPartyCategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class ThirdPartyCategoryRepositoryAdapter implements ThirdPartyCategoryRepository {

    private final ThirdPartyCategoryJpaRepository jpa;
    private final ThirdPartyCategoryMapper mapper;

    ThirdPartyCategoryRepositoryAdapter(ThirdPartyCategoryJpaRepository jpa, ThirdPartyCategoryMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public ThirdPartyCategory save(ThirdPartyCategory category) {
        var entity = mapper.toEntity(category);
        return mapper.toDomain(jpa.save(entity));
    }

    @Override
    public Optional<ThirdPartyCategory> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ThirdPartyCategory> findAllActive() {
        return jpa.findByActiveTrue().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
