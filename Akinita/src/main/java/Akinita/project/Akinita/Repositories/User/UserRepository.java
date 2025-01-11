package Akinita.project.Akinita.Repositories.User;

import Akinita.project.Akinita.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
