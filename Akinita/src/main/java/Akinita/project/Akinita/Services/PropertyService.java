package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Repositories.RealEstate.*;
import Akinita.project.Akinita.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final HouseRepository houseRepository;

    private final LandRepository landRepository;

    private final ParkingRepository parkingRepository;

    private final CommercialPropertyRepository commercialPropertyRepository;

    @Autowired
    public PropertyService(HouseRepository houseRepository,
                           LandRepository landRepository, ParkingRepository parkingRepository,
                           CommercialPropertyRepository commercialPropertyRepository) {
        this.houseRepository = houseRepository;
        this.landRepository = landRepository;
        this.parkingRepository = parkingRepository;
        this.commercialPropertyRepository = commercialPropertyRepository;
    }

    public int SaveProperty(String propertyType, Property newProperty) {                 // Property Repository den exei bean , opote tha mpei elegxos

        switch(propertyType) {
            case "House":

                houseRepository.save((House)newProperty);                                  // xanontai ta dedomena , alla to afhnoume proswrina
                return houseRepository.findIdByProperty((House)newProperty);

            case "Land":

                landRepository.save((Land)newProperty);
                return landRepository.findIdByProperty((Land)newProperty);

            case "Parking":

                parkingRepository.save((Parking)newProperty);
                return parkingRepository.findIdByProperty((Parking)newProperty);

            case "CommercialProperty":

                commercialPropertyRepository.save((CommercialProperty)newProperty);
                return commercialPropertyRepository.findIdByProperty((CommercialProperty)newProperty);

            default:
                throw new IllegalArgumentException("Invalid property type");
        }

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
