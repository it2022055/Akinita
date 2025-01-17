package Akinita.project.Akinita.Repositories;


import Akinita.project.Akinita.Entities.RentalApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface RentalApplicationRepository extends JpaRepository<RentalApplication, Integer> {

    List<RentalApplication> findByOwnerId(int ownerId);

}
