package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.entities.Owner;
import Akinita.project.Akinita.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NoRepositoryBean
public interface PropertyRepository<T,ID> extends JpaRepository<T, ID>{

    List<T> findByEstateName(String estateName);

    List<T> findByLocation(String location);

    List<T> findByPriceGreaterThanEqual(int price);

    List<T> findByPriceLessThanEqual(int price);

    List<T> findByDescriptionContaining(String keyword);

    List<T> findByAvailability(Boolean availability);

    List<T> findByOwner(Owner owner);

    List<T> findByOwnerId(Integer ownerId);

    @Query("SELECT p.id FROM Property p WHERE p = :property")
    int findIdByProperty(@Param("property") T property);

}