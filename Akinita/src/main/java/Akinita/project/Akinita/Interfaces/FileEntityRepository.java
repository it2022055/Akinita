package Akinita.project.Akinita.Interfaces;

import Akinita.project.Akinita.Entities.FileEntity;
import Akinita.project.Akinita.Entities.RentalApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileEntityRepository extends JpaRepository<FileEntity, Integer> {

    @Modifying
    @Query("DELETE FROM FileEntity f WHERE f.rentalApplication.id = :rentalApplicationId")
    void deleteByRentalApplicationId(@Param("rentalApplicationId") int rentalApplicationId);

}
