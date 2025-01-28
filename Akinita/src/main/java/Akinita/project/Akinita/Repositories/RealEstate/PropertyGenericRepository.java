package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Properties.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PropertyGenericRepository<T,ID> extends JpaRepository<T, ID>{

    Property findPropertyById(Integer propertyId);

}