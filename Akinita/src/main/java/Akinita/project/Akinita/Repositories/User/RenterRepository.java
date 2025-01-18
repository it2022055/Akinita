package Akinita.project.Akinita.Repositories.User;

import Akinita.project.Akinita.Entities.Owner;
import Akinita.project.Akinita.Entities.Renter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Integer> {

    @Query("SELECT o FROM Owner o WHERE o.user.username = :username")
    Renter findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Renter (user_id, first_name, last_name, telephone_number) " +
            "VALUES (:userId, :firstName, :lastName, :telephoneNumber)", nativeQuery = true)
    void saveRenterCustom(Integer userId, String firstName, String lastName, String telephoneNumber);

    @Query("SELECT o FROM Owner o WHERE o.user.email = :email")
    Renter findByEmail(String email);

    Renter findByUserId(Integer userId);
}
