package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Properties.House;
import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Entities.Properties.CommercialProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommercialPropertyGenericRepository extends PropertyGenericRepository<CommercialProperty, Integer>{

    @Query("SELECT p FROM Property p JOIN CommercialProperty c ON p.id = c.id WHERE p.location = :location")
    List<Property> findCommonPropertiesByLocation(@Param("location") String location);

    @Query("SELECT p FROM Property p JOIN CommercialProperty c ON  p.id = c.id")
    List<Property> findAllProperties();

    @Query("SELECT c FROM CommercialProperty c JOIN Property p ON p.id = c.id WHERE p.id = :id")
    House findByCommercialPropertyId(@Param("id") Integer id);

    @Query("SELECT h FROM CommercialProperty h WHERE h.owner.userId = :userId")
    List<CommercialProperty> findByOwnerId(@Param("userId") int userId);

    List<CommercialProperty> findByVisibilityOrVisibilityAndOwner_UserId(String visibility1, String visibility2, int ownerId);

}