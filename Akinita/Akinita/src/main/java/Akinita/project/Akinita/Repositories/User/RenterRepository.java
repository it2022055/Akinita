package Akinita.project.Akinita.Repositories.User;

import Akinita.project.Akinita.entities.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Integer> {
}
