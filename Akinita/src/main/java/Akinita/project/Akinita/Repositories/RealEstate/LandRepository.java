package Akinita.project.Akinita.Repositories.RealEstate;

import Akinita.project.Akinita.Entities.Properties.Land;
import org.springframework.stereotype.Repository;

@Repository
public interface LandRepository extends PropertyRepository<Land, Integer> {
}