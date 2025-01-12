package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.BuildingFees;
import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.ConstructionDate;
import Akinita.project.Akinita.entities.CommercialProperty;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialPropertyRepository extends RealEstateRepository<CommercialProperty, Integer>, ConstructionDate<CommercialProperty, Integer>, BuildingFees<CommercialProperty, Integer> {
}