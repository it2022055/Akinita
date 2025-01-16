package Akinita.project.Akinita.Repositories.User;

import Akinita.project.Akinita.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    @Query("SELECT o FROM Owner o WHERE o.user.username = :username")
    Owner findByUsername(String username);
}
