package Akinita.project.Akinita.Repositories.User;

import Akinita.project.Akinita.Entities.Actors.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findById(Integer userId);

    boolean existsByUsername(String username);
}
