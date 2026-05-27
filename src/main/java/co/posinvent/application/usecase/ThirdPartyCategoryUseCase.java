package co.posinvent.application.usecase;

import co.posinvent.application.dto.ThirdPartyCategoryRequest;
import co.posinvent.application.dto.ThirdPartyCategoryResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.ThirdPartyCategory;
import co.posinvent.domain.repository.ThirdPartyCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ThirdPartyCategoryUseCase {

    private final ThirdPartyCategoryRepository thirdPartyCategoryRepository;

    public ThirdPartyCategoryUseCase(ThirdPartyCategoryRepository thirdPartyCategoryRepository) {
        this.thirdPartyCategoryRepository = thirdPartyCategoryRepository;
    }

    @Transactional(readOnly = true)
    public List<ThirdPartyCategoryResponse> listActive() {
        return thirdPartyCategoryRepository.findAllActive().stream()
                .map(ThirdPartyCategoryResponse::from)
                .toList();
    }

    @Transactional
    public ThirdPartyCategoryResponse create(ThirdPartyCategoryRequest request) {
        boolean nameExists = thirdPartyCategoryRepository.findAllActive().stream()
                .anyMatch(c -> c.name().equalsIgnoreCase(request.name()));
        if (nameExists) {
            throw new BusinessException("DUPLICATE_CATEGORY_NAME",
                    "Ya existe una categoría con el nombre: " + request.name());
        }

        var category = new ThirdPartyCategory(null, request.name(), request.baseType(), true);
        return ThirdPartyCategoryResponse.from(thirdPartyCategoryRepository.save(category));
    }
}
