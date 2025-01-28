package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Properties.Property;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PropertyRepository extends PropertyGenericRepository<Property,Integer>{

    @Query("SELECT p FROM Property p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Property> findPropertiesWithinPriceRange(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);

    @Query("SELECT p FROM Property p  WHERE p.location = :location")
    List<Property> findPropertiesByLocation(@Param("location") String location);


    List<Property> findByVisibility(String visibility);

    @Modifying
    @Transactional
    @Query("DELETE FROM Property p WHERE p.owner.userId = :ownerId")
    void deleteByOwnerId(@Param("ownerId") Integer ownerId);

    @Query("SELECT r FROM Property r WHERE r.renter.userId=:renterId")
    List<Property> findByRenterId(@Param("renterId") Integer renterId);
}
