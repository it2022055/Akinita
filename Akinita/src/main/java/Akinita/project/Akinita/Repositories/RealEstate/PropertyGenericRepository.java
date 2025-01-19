package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Actors.Owner;
import Akinita.project.Akinita.Entities.Properties.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface PropertyGenericRepository<T,ID> extends JpaRepository<T, ID>{

    List<T> findByEstateName(String estateName);

    @Query("SELECT p FROM Property p WHERE p.availability = true AND p.location = :location")
    List<Property> findByLocation(@Param("location") String location);

    List<T> findByPriceGreaterThanEqual(Double price);

    List<T> findByPriceLessThanEqual(Double price);

    List<T> findBySquareMeterGreaterThanEqual(Double size);

    List<T> findBySquareMeterLessThanEqual(Double size);

    List<T> findByDescriptionContaining(String keyword);

    List<T> findByAvailability(Boolean availability);

    List<T> findByOwner(Owner owner);

    List<Property> findByOwner_UserId(Integer userId);

    Property findPropertyById(Integer propertyId);

    @Query("SELECT p.id FROM Property p WHERE p = :property")
    int findIdByProperty(@Param("property") T property);

    @Query("SELECT p.owner.userId FROM Property p WHERE p.id = :propertyId")
    Integer findOwnerIdByPropertyId(@Param("propertyId") int propertyId);
}