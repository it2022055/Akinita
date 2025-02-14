package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Enums.EnergyClass;
import Akinita.project.Akinita.Entities.Enums.Facilities;
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
    CommercialProperty findByCommercialPropertyId(@Param("id") Integer id);

    @Query("SELECT h FROM CommercialProperty h WHERE h.owner.userId = :userId")
    List<CommercialProperty> findByOwnerId(@Param("userId") int userId);

    List<CommercialProperty> findByVisibilityOrVisibilityAndOwner_UserId(String visibility1, String visibility2, int ownerId);

    @Query("SELECT CASE WHEN COUNT(cf) = :facilitiesSize THEN true ELSE false END " +
            "FROM CommercialProperty c JOIN c.facilities cf " +
            "WHERE c.id = :comPropId AND cf IN :facilities")
    Boolean hasFacilities(@Param("comPropId") Integer comPropId, @Param("facilities") List<Facilities> facilities, @Param("facilitiesSize") int facilitiesSize);

    @Query("SELECT CASE WHEN c.energyClass = :energyClass THEN true ELSE false END FROM CommercialProperty c WHERE c.id = :comPropId")
    Boolean hasEnergyClass(@Param("comPropId") Integer comPropId, @Param("energyClass") EnergyClass energyClass);

}