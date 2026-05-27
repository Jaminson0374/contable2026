package co.posinvent.application.usecase;

import co.posinvent.application.dto.ProductModelRequest;
import co.posinvent.application.dto.ProductModelResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.ProductModel;
import co.posinvent.domain.repository.ProductModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductModelUseCase {

    private final ProductModelRepository repository;

    public ProductModelUseCase(ProductModelRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ProductModelResponse> listAll() {
        return repository.findAllActive().stream().map(ProductModelResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductModelResponse> listByBrand(UUID brandId) {
        return repository.findByBrandId(brandId).stream().map(ProductModelResponse::from).toList();
    }

    @Transactional
    public ProductModelResponse create(ProductModelRequest request) {
        if (repository.existsByName(request.name())) {
            throw new BusinessException("DUPLICATE_NAME", "Ya existe un modelo con ese nombre.");
        }
        var entity = new ProductModel(null, request.name(), request.brandId(), true, null);
        return ProductModelResponse.from(repository.save(entity));
    }
}
