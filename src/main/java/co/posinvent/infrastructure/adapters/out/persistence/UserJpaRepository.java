package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsernameAndActiveTrue(String username);
    Optional<UserEntity> findByUsername(String username);

    @Query("""
        SELECT u FROM UserEntity u JOIN u.role r
        WHERE (:search IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:roleName IS NULL OR r.name = :roleName)
          AND (:active IS NULL OR u.active = :active)
        ORDER BY u.fullName
    """)
    Page<UserEntity> findFiltered(
        @Param("search") String search,
        @Param("roleName") String roleName,
        @Param("active") Boolean active,
        Pageable pageable
    );

    long countByRoleNameAndActive(String roleName, boolean active);
}
