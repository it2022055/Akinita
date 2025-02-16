package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Enums.EnergyClass;
import Akinita.project.Akinita.Entities.Enums.Facilities;
import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Entities.Properties.House;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseGenericRepository extends PropertyGenericRepository<House, Integer>{

    @Query("SELECT h FROM House h JOIN Property p ON p.id = h.id WHERE p.id = :id")
    House findByHouseId(@Param("id") Integer id);

    @Query("SELECT p FROM Property p JOIN House h ON p.id = h.id  WHERE p.location = :location")
    List<Property> findCommonPropertiesByLocation(@Param("location") String location);

    @Query("SELECT p FROM Property p JOIN House h ON  p.id = h.id")
    List<Property> findAllProperties();

    @Query("SELECT h FROM House h WHERE h.owner.userId = :userId")
    List<House> findByOwnerId(@Param("userId") int userId);

    List<House> findByVisibilityOrVisibilityAndOwner_UserId(String visibility1, String visibility2, int ownerId);

    @Query("SELECT CASE WHEN COUNT(hf) = :facilitiesSize THEN true ELSE false END " +
            "FROM House h JOIN h.facilities hf " +
            "WHERE h.id = :houseId AND hf IN :facilities")
    Boolean hasFacilities(@Param("houseId") Integer houseId, @Param("facilities") List<Facilities> facilities, @Param("facilitiesSize") int facilitiesSize);

    @Query("SELECT CASE WHEN h.energyClass = :energyClass THEN true ELSE false END FROM House h WHERE h.id = :houseId")
    Boolean hasEnergyClass(@Param("houseId") Integer houseId, @Param("energyClass") EnergyClass energyClass);

    @Query("SELECT h.facilities FROM House h WHERE h.id = :propertyId")
    List<Facilities> findFacilitiesByPropertyId(@Param("propertyId") Integer propertyId);

}