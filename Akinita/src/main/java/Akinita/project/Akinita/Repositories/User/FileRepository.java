package Akinita.project.Akinita.Repositories.User;

import Akinita.project.Akinita.Entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    List<FileEntity> findByRentalApplicationId(int rentalApplicationId);

    @Query("SELECT fe FROM FileEntity fe WHERE fe.rentalApplication.id = :appId" )
    List<FileEntity> findAllById(Integer appId);

    @Query("SELECT fe.id FROM FileEntity fe WHERE fe.rentalApplication.id = :appId" )
    List<Integer> findAllTheId(Integer appId);

    @Query("SELECT fe FROM FileEntity fe WHERE fe.rentalApplication.id = :appId AND fe.id = :fileId" )
    FileEntity findByIdAndAppId(Integer fileId, Integer appId);

}
