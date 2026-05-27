package co.posinvent.domain.repository;

import co.posinvent.domain.model.ThirdPartyCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ThirdPartyCategoryRepository {

    ThirdPartyCategory save(ThirdPartyCategory category);

    Optional<ThirdPartyCategory> findById(UUID id);

    List<ThirdPartyCategory> findAllActive();
}
