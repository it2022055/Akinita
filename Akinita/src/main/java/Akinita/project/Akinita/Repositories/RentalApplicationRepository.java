package Akinita.project.Akinita.Repositories;


import Akinita.project.Akinita.Entities.RentalApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface RentalApplicationRepository extends JpaRepository<RentalApplication, Integer> {

    @Query ("SELECT r.owner.id FROM RentalApplication r WHERE r.owner.id = :ownerId")
    List<RentalApplication> findByOwnerId(int ownerId);

    @Query ("SELECT r.renter.id FROM RentalApplication r WHERE r.renter.id = :renterId")
    List<RentalApplication> findByRenterId(int renterId);

    @Query ("SELECT r.property.id FROM RentalApplication r WHERE r.property.id = :propertyId")
    List<RentalApplication> findByPropertyId(int propertyId);

    RentalApplication findById(int id);
}
