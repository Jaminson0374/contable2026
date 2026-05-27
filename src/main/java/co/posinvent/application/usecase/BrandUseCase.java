package co.posinvent.application.usecase;

import co.posinvent.application.dto.BrandRequest;
import co.posinvent.application.dto.BrandResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.Brand;
import co.posinvent.domain.repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandUseCase {

    private final BrandRepository repository;

    public BrandUseCase(BrandRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> listAll() {
        return repository.findAllActive().stream().map(BrandResponse::from).toList();
    }

    @Transactional
    public BrandResponse create(BrandRequest request) {
        if (repository.existsByName(request.name())) {
            throw new BusinessException("DUPLICATE_NAME", "Ya existe una marca con ese nombre.");
        }
        var entity = new Brand(null, request.name(), true, null);
        return BrandResponse.from(repository.save(entity));
    }
}
