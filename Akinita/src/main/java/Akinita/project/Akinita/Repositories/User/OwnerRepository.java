package Akinita.project.Akinita.Repositories.User;

import Akinita.project.Akinita.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {         // ti an to kaname kai auto generic??
}
