package co.posinvent.domain.repository;

import co.posinvent.domain.model.PriceList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PriceListRepository {
    List<PriceList> findAllActive();
    Optional<PriceList> findById(UUID id);
    PriceList save(PriceList entity);
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, UUID id);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
