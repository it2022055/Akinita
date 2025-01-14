package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Repositories.RealEstate.PropertyRepository;
import Akinita.project.Akinita.entities.Property;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    PropertyRepository propertyRepository;

    public int SaveProperty(RealEstate property) {
        propertyRepository.save(property);
        return propertyRepository.findIdByProperty(property);
    }

    public List<Property> findAllProperties() {
        return propertyRepository.findAll();
    }
}
