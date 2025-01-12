package Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;
import java.util.List;

@NoRepositoryBean
public interface ConstructionDate<T,ID> extends  JpaRepository<T, ID> {

    List<T> findByConstructionDateBefore(Date date);

    List<T> findByConstructionDateAfter(Date date);

}
