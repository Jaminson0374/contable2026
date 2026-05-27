package co.posinvent.domain.repository;

import co.posinvent.domain.model.City;
import co.posinvent.domain.model.Department;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository {

    List<Department> findAll();

    List<City> findCitiesByDepartmentId(UUID departmentId);
}
