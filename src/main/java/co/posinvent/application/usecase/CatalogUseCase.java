package co.posinvent.application.usecase;

import co.posinvent.application.dto.CityResponse;
import co.posinvent.application.dto.DepartmentResponse;
import co.posinvent.application.dto.IdentificationTypeRequest;
import co.posinvent.application.dto.IdentificationTypeResponse;
import co.posinvent.domain.model.IdentificationType;
import co.posinvent.domain.repository.DepartmentRepository;
import co.posinvent.domain.repository.IdentificationTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CatalogUseCase {

    private final IdentificationTypeRepository identificationTypeRepository;
    private final DepartmentRepository departmentRepository;

    public CatalogUseCase(
            IdentificationTypeRepository identificationTypeRepository,
            DepartmentRepository departmentRepository
    ) {
        this.identificationTypeRepository = identificationTypeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<IdentificationTypeResponse> listIdentificationTypes() {
        return identificationTypeRepository.findAllActive().stream()
                .map(IdentificationTypeResponse::from)
                .toList();
    }

    @Transactional
    public IdentificationTypeResponse createIdentificationType(IdentificationTypeRequest request) {
        IdentificationType type = new IdentificationType(null, request.code(), request.name(), request.requiresDv(), true);
        return IdentificationTypeResponse.from(identificationTypeRepository.save(type));
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> listDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CityResponse> listCitiesByDepartment(UUID departmentId) {
        return departmentRepository.findCitiesByDepartmentId(departmentId).stream()
                .map(CityResponse::from)
                .toList();
    }
}
