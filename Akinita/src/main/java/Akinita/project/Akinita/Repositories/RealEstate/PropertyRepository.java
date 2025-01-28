package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Properties.Property;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends PropertyGenericRepository<Property,Integer>{

    @Query("SELECT p FROM Property p  WHERE p.location = :location")
    List<Property> findPropertiesByLocation(@Param("location") String location);

    List<Property> findByVisibility(String visibility);

    @Query("SELECT r FROM Property r WHERE r.renter.userId=:renterId")
    List<Property> findByRenterId(@Param("renterId") Integer renterId);
}
