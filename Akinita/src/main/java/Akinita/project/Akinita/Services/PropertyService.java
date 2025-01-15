package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Repositories.RealEstate.*;
import Akinita.project.Akinita.entities.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    PropertyRepository propertyRepository;
    HouseRepository houseRepository;
    LandRepository landRepository;
    ParkingRepository parkingRepository;
    CommercialPropertyRepository commercialPropertyRepository;

    public int SaveProperty(RealEstate property) {
        propertyRepository.save(property);
        return propertyRepository.findIdByProperty(property);
    }

    public List<? extends Property> findAllProperties(String location, String propertyType) {
        return switch (propertyType) {
            case "House" -> houseRepository.findByLocation(location);
            case "Land" -> landRepository.findByLocation(location);
            case "Parking" -> parkingRepository.findByLocation(location);
            case "CommercialProperty" -> commercialPropertyRepository.findByLocation(location);
            default -> throw new IllegalArgumentException("Invalid property type");
        };
    }
}
