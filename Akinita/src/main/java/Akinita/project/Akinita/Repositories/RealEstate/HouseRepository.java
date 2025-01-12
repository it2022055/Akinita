package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.BuildingFees;
import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.ConstructionDate;
import Akinita.project.Akinita.entities.House;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends RealEstateRepository<House, Integer>, ConstructionDate<House, Integer>, BuildingFees<House, Integer> {
}