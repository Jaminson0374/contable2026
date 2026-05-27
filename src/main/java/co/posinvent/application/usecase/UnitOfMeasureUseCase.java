package co.posinvent.application.usecase;

import co.posinvent.application.dto.UnitOfMeasureRequest;
import co.posinvent.application.dto.UnitOfMeasureResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.UnitOfMeasure;
import co.posinvent.domain.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnitOfMeasureUseCase {

    private final UnitOfMeasureRepository repository;

    public UnitOfMeasureUseCase(UnitOfMeasureRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<UnitOfMeasureResponse> listAll() {
        return repository.findAllActive().stream().map(UnitOfMeasureResponse::from).toList();
    }

    @Transactional
    public UnitOfMeasureResponse create(UnitOfMeasureRequest request) {
        if (repository.existsByName(request.name())) {
            throw new BusinessException("DUPLICATE_NAME", "Ya existe una unidad de medida con ese nombre.");
        }
        var entity = new UnitOfMeasure(null, request.code(), request.name(), request.baseUnit(), true, null);
        return UnitOfMeasureResponse.from(repository.save(entity));
    }
}
