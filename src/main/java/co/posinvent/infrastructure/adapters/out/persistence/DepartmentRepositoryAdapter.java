package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.City;
import co.posinvent.domain.model.Department;
import co.posinvent.domain.repository.DepartmentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
class DepartmentRepositoryAdapter implements DepartmentRepository {

    private final DepartmentJpaRepository jpa;
    private final DepartmentMapper departmentMapper;
    private final CityJpaRepository cityJpa;
    private final CityMapper cityMapper;

    DepartmentRepositoryAdapter(
            DepartmentJpaRepository jpa,
            DepartmentMapper departmentMapper,
            CityJpaRepository cityJpa,
            CityMapper cityMapper
    ) {
        this.jpa = jpa;
        this.departmentMapper = departmentMapper;
        this.cityJpa = cityJpa;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<Department> findAll() {
        return jpa.findAll().stream()
                .map(departmentMapper::toDomain)
                .toList();
    }

    @Override
    public List<City> findCitiesByDepartmentId(UUID departmentId) {
        return cityJpa.findByDepartmentId(departmentId).stream()
                .map(cityMapper::toDomain)
                .toList();
    }
}
