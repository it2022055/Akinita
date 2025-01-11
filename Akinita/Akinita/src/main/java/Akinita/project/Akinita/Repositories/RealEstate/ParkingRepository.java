package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.entities.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking,Integer> {
}
