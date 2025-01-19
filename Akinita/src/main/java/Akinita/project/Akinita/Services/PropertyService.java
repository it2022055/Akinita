package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Properties.Land;
import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Entities.PropertySpecifications;
import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Repositories.RealEstate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    PropertyRepository propertyRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    LandRepository landRepository;
    @Autowired
    ParkingRepository parkingRepository;
    @Autowired
    CommercialPropertyRepository commercialPropertyRepository;

    public int SaveProperty(RealEstate property) {
        propertyRepository.save(property);
        return propertyRepository.findIdByProperty(property);
    }

    public List<? extends Property> findAllPropertiesByLocation(String location, String propertyType) {
        return switch (propertyType) {
            case "House" -> houseRepository.findByLocation(location);
            case "Land" -> landRepository.findByLocation(location);
            case "Parking" -> parkingRepository.findByLocation(location);
            case "CommercialProperty" -> commercialPropertyRepository.findByLocation(location);
            default -> throw new IllegalArgumentException("Invalid property type");
        };
    }

    public List<Property> findProperties(String area, String propertyType, Double minPrice, Double maxPrice, Double minSize, Double maxSize, String buildingFees, String constructionDate) {
        // Δημιουργία του query με βάση τα φίλτρα
        Specification<Property> spec = Specification.where(null);

        if (area != null && !area.isEmpty()) {
            spec = spec.and(PropertySpecifications.hasLocation(area));
        }
        if (propertyType != null && !propertyType.isEmpty() && !"All".equalsIgnoreCase(propertyType)) {
            spec = spec.and(PropertySpecifications.hasPropertyType(propertyType));
        }
        if (minPrice != null) {
            spec = spec.and(PropertySpecifications.hasMinPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(PropertySpecifications.hasMaxPrice(maxPrice));
        }
        if (minSize != null) {
            spec = spec.and(PropertySpecifications.hasMinSize(minSize));
        }
        if (maxSize != null) {
            spec = spec.and(PropertySpecifications.hasMaxSize(maxSize));
        }

        if (buildingFees != null && !buildingFees.isEmpty()) {
            spec = spec.and(PropertySpecifications.hasBuildingFees(buildingFees));
        }
        if (constructionDate != null && !constructionDate.isEmpty()) {
            spec = spec.and(PropertySpecifications.hasConstructionDate(constructionDate));
        }

        return propertyRepository.findAll((Example) spec);
    }



    public List findAllProperties() {
        return propertyRepository.findByAvailability(true);
    }

    public Property getPropertyById(int id) {
        return propertyRepository.findPropertyById(id);
    }

    public void SaveLandProperty(Land land) {
        landRepository.save(land);
    }
}
