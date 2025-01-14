package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface RealEstateRepository<T,ID> extends JpaRepository<T, ID>{

    List<T> findByEstateName(String estateName);

    List<T> findByLocation(String location);

    List<T> findByPriceGreaterThanEqual(int price);

    List<T> findByPriceLessThanEqual(int price);

    List<T> findByDescriptionContaining(String keyword);

    List<T> findByAvailability(Boolean availability);

    List<T> findByOwner(Owner owner);

}