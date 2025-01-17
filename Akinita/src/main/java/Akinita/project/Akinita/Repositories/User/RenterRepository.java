package Akinita.project.Akinita.Repositories.User;

import Akinita.project.Akinita.Entities.Owner;
import Akinita.project.Akinita.Entities.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Integer> {

}
