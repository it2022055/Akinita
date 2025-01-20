package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.ConstructionDate;
import Akinita.project.Akinita.Entities.Properties.Parking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ParkingGenericRepository extends PropertyGenericRepository<Parking,Integer>, ConstructionDate<Parking, Integer> {
    List<Parking> findByConstructionDate(@Param("construction_date") Date construction_date);

    @Query("SELECT p FROM Property p JOIN Parking pr ON p.location = pr.location AND p.id = pr.id WHERE p.location = :location")
    List<Property> findCommonProperties(@Param("location") String location);
}