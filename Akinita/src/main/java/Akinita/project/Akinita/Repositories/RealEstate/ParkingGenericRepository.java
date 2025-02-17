package Akinita.project.Akinita.Repositories.RealEstate;


import Akinita.project.Akinita.Entities.Enums.EnergyClass;
import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Entities.Properties.Parking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingGenericRepository extends PropertyGenericRepository<Parking,Integer>{

    @Query("SELECT p FROM Property p JOIN Parking pr ON p.id = pr.id WHERE p.location = :location")
    List<Property> findCommonPropertiesByLocation(@Param("location") String location);

    @Query("SELECT p FROM Property p JOIN Parking pr ON  p.id = pr.id ")
    List<Property> findAllProperties();

    @Query("SELECT pr FROM Parking pr JOIN Property p ON p.id = pr.id WHERE p.id = :id")
    Parking findByParkingId(@Param("id") Integer id);

    List<Parking> findByVisibilityOrVisibilityAndOwner_UserId(String visibility1, String visibility2, int ownerId);

    @Query("SELECT CASE WHEN p.energyClass = :energyClass THEN true ELSE false END FROM Parking p WHERE p.id = :parkingId")
    Boolean hasEnergyClass(@Param("parkingId") Integer parkingId, @Param("energyClass") EnergyClass energyClass);

    @Query("SELECT p FROM Parking p WHERE p.owner.userId = :userId")
    List<Parking> findByOwnerId(@Param("userId") int userId);

}