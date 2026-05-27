package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.User;
import co.posinvent.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpa;
    private final UserMapper mapper;

    UserRepositoryAdapter(UserJpaRepository jpa, UserMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public User save(User domain) {
        var entity = mapper.toEntity(domain);
        // Para update: mantener passwordHash existente
        if (domain.id() != null) {
            jpa.findById(domain.id()).ifPresent(existing -> {
                entity.setPasswordHash(existing.getPasswordHash());
            });
        }
        return mapper.toDomain(jpa.save(entity));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpa.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }

    @Override
    public Page<User> findFiltered(String search, String roleName, Boolean active, Pageable pageable) {
        return jpa.findFiltered(search, roleName, active, pageable).map(mapper::toDomain);
    }

    @Override
    public long countByRoleNameAndActive(String roleName, boolean active) {
        return jpa.countByRoleNameAndActive(roleName, active);
    }
}
