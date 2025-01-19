package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Properties.Property;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends PropertyGenericRepository<Property,Integer>{
}
