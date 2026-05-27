package co.posinvent.domain.repository;

import co.posinvent.domain.model.CompanyConfig;

import java.util.Optional;

public interface CompanyConfigRepository {
    Optional<CompanyConfig> findConfig();
    CompanyConfig save(CompanyConfig config);
}
