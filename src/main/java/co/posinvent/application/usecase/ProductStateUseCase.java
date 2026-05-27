package co.posinvent.application.usecase;

import co.posinvent.application.dto.ProductStateRequest;
import co.posinvent.application.dto.ProductStateResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.ProductState;
import co.posinvent.domain.repository.ProductStateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductStateUseCase {

    private final ProductStateRepository repository;

    public ProductStateUseCase(ProductStateRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ProductStateResponse> listAll() {
        return repository.findAllActive().stream().map(ProductStateResponse::from).toList();
    }

    @Transactional
    public ProductStateResponse create(ProductStateRequest request) {
        if (repository.existsByName(request.name())) {
            throw new BusinessException("DUPLICATE_NAME", "Ya existe un estado de artículo con ese nombre.");
        }
        var entity = new ProductState(null, request.code(), request.name(), true, null);
        return ProductStateResponse.from(repository.save(entity));
    }
}
