package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Properties.Land;
import Akinita.project.Akinita.Entities.Properties.Property;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandGenericRepository extends PropertyGenericRepository<Land, Integer> {

    @Query("SELECT p FROM Property p JOIN Land l ON p.id = l.id WHERE p.location = :location")
    List<Property> findCommonPropertiesByLocation(@Param("location") String location);

    @Query("SELECT p FROM Property p JOIN Land l ON  p.id = l.id ")
    List<Property> findAllProperties();

    List<Land> findByVisibilityOrVisibilityAndOwner_UserId(String visibility1, String visibility2, int ownerId);

    @Query("SELECT l FROM Land l WHERE l.owner.userId = :userId")
    List<Land> findByOwnerId(@Param("userId") int userId);
}