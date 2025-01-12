package Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BuildingFees <T, ID> extends  JpaRepository<T, ID> {

    List<T> findByBuildingFees(boolean facilities);

}