package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Properties.CommercialProperty;
import Akinita.project.Akinita.Entities.Properties.House;
import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Entities.Properties.Parking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ParkingGenericRepository extends PropertyGenericRepository<Parking,Integer>{
    List<Parking> findByConstructionDate(@Param("construction_date") Date construction_date);

    @Query("SELECT p FROM Property p JOIN Parking pr ON p.id = pr.id WHERE p.location = :location")
    List<Property> findCommonPropertiesByLocation(@Param("location") String location);

    @Query("SELECT p FROM Property p JOIN Parking pr ON  p.id = pr.id ")
    List<Property> findAllProperties();

    @Query("SELECT pr FROM Parking pr JOIN Property p ON p.id = pr.id WHERE p.id = :id")
    Parking findByParkingId(@Param("id") Integer id);

    List<Parking> findByVisibilityOrVisibilityAndOwner_UserId(String visibility1, String visibility2, int ownerId);
}