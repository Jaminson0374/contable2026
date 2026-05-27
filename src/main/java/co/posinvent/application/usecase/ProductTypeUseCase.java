package co.posinvent.application.usecase;

import co.posinvent.application.dto.ProductTypeRequest;
import co.posinvent.application.dto.ProductTypeResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.ProductType;
import co.posinvent.domain.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductTypeUseCase {

    private final ProductTypeRepository repository;

    public ProductTypeUseCase(ProductTypeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ProductTypeResponse> listAll() {
        return repository.findAllActive().stream().map(ProductTypeResponse::from).toList();
    }

    @Transactional
    public ProductTypeResponse create(ProductTypeRequest request) {
        if (repository.existsByName(request.name())) {
            throw new BusinessException("DUPLICATE_NAME", "Ya existe un tipo de artículo con ese nombre.");
        }
        var entity = new ProductType(null, request.code(), request.name(), true, null);
        return ProductTypeResponse.from(repository.save(entity));
    }
}
