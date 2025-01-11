package Akinita.project.Akinita.Repositories;

import Akinita.project.Akinita.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
