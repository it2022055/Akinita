package Akinita.project.Akinita.Repositories;


import Akinita.project.Akinita.Entities.RentalApplication;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface RentalApplicationRepository extends JpaRepository<RentalApplication, Integer> {

    @Query("SELECT r FROM RentalApplication r WHERE r.owner.id = :ownerId")
    List<RentalApplication> findByOwnerId(@Param("ownerId") Integer ownerId);

    @Query("SELECT r FROM RentalApplication r WHERE r.renter.id = :renterId")
    List<RentalApplication> findByRenterId(int renterId);

    @Query("SELECT r FROM RentalApplication r WHERE r.property.id = :propertyId")
    RentalApplication findByPropertyId(int propertyId);

    RentalApplication findById(int id);

    @Modifying
    @Transactional
    @Query("UPDATE RentalApplication ra SET ra.Status = :status")
    void setStatus(@Param("status") Boolean status);

    @Modifying
    @Transactional
    @Query("DELETE FROM RentalApplication r WHERE r.owner.id = :ownerId")
    void deleteByOwnerId(@Param("ownerId") int ownerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RentalApplication r WHERE r.renter.id = :renterId")
    void deleteByRenterId(@Param("renterId") int renterId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RentalApplication r WHERE r.property.id = :propertyId")
    void deleteByPropertyId(@Param("propertyId") int propertyId);




    @Query ("UPDATE RentalApplication ra SET ra.rentalDate = :currDate WHERE ra.id = :applicationId")
    void setDateCurrDate(@Param("applicationId") Integer applicationId, @Param("currDate") Date currDate);

    @Modifying
    @Transactional
    @Query("UPDATE RentalApplication ra SET ra.Status = :status WHERE ra.property.id = :prId")
    void setStatus(@Param("status") Boolean status, Integer prId);


}
