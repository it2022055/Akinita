package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.ConstructionDate;
import Akinita.project.Akinita.Entities.Properties.Parking;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ParkingGenericRepository extends PropertyGenericRepository<Parking,Integer>, ConstructionDate<Parking, Integer> {
    List<Parking> findByConstructionDate(@Param("construction_date") Date construction_date);
}