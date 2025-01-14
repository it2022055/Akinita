package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.ConstructionDate;
import Akinita.project.Akinita.entities.Parking;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends PropertyRepository<Parking,Integer>, ConstructionDate<Parking, Integer> {
}