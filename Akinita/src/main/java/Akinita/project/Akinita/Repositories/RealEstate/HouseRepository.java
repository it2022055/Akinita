package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.BuildingFees;
import Akinita.project.Akinita.Repositories.RealEstate.LimitedMethods.ConstructionDate;
import Akinita.project.Akinita.Entities.Properties.House;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HouseRepository extends PropertyRepository<House, Integer>, ConstructionDate<House, Integer>, BuildingFees<House, Integer> {

    @Query("SELECT h FROM House h WHERE h.buildingFees = true")
    List<House> findByBuildingFees(@Param("building_fees") boolean buildingFees);

    List<House> findByConstructionDate(@Param("construction_date") Date construction_date);


}