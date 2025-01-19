package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.BuildingFees;
import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.ConstructionDate;
import Akinita.project.Akinita.Entities.Properties.CommercialProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommercialPropertyGenericRepository extends PropertyGenericRepository<CommercialProperty, Integer>, ConstructionDate<CommercialProperty, Integer>, BuildingFees<CommercialProperty, Integer> {
    @Query("SELECT c FROM CommercialProperty c WHERE c.buildingFees = true")
    List<CommercialProperty> findByBuildingFees(@Param("building_fees") boolean buildingFees);

    List<CommercialProperty> findByConstructionDate(@Param("construction_date") Date construction_date);

}