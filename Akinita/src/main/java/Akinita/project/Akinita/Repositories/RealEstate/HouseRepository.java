package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.BuildingFees;
import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.ConstructionDate;
import Akinita.project.Akinita.Entities.Properties.House;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends PropertyRepository<House, Integer>, ConstructionDate<House, Integer>, BuildingFees<House, Integer> {
}