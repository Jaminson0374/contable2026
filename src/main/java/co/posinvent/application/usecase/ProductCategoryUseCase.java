package co.posinvent.application.usecase;

import co.posinvent.application.dto.ProductCategoryRequest;
import co.posinvent.application.dto.ProductCategoryResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.ProductCategory;
import co.posinvent.domain.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryUseCase {

    private final ProductCategoryRepository repository;

    public ProductCategoryUseCase(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ProductCategoryResponse> listAll() {
        return repository.findAllActive().stream().map(ProductCategoryResponse::from).toList();
    }

    @Transactional
    public ProductCategoryResponse create(ProductCategoryRequest request) {
        if (repository.existsByName(request.name())) {
            throw new BusinessException("DUPLICATE_NAME", "Ya existe una categoría de artículo con ese nombre.");
        }
        var entity = new ProductCategory(null, request.name(), true, null);
        return ProductCategoryResponse.from(repository.save(entity));
    }
}
