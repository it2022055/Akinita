package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Repositories.RealEstate.CommercialPropertyRepository;
import Akinita.project.Akinita.Repositories.RealEstate.HouseRepository;
import Akinita.project.Akinita.Repositories.RealEstate.LandRepository;
import Akinita.project.Akinita.Repositories.RealEstate.ParkingRepository;
import Akinita.project.Akinita.Repositories.RentalApplicationRepository;
import Akinita.project.Akinita.Repositories.User.OwnerRepository;
import Akinita.project.Akinita.Entities.Owner;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private LandRepository landRepository;
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private CommercialPropertyRepository commercialPropertyRepository;
   @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private RentalApplicationRepository rentalApplicationRepository;

    // Μέθοδος που επιστρέφει τα ακίνητα του ιδιοκτήτη
    public List<RealEstate> getOwnerProperties(int ownerId) {
        List<RealEstate> ownerProperties = new ArrayList<>();

        //Ανάκτηση ακινήτων για κάθε τύπο
        ownerProperties.addAll(houseRepository.findByOwner_UserId(ownerId));
        ownerProperties.addAll(landRepository.findByOwner_UserId(ownerId));
        ownerProperties.addAll(parkingRepository.findByOwner_UserId(ownerId));
        ownerProperties.addAll(commercialPropertyRepository.findByOwner_UserId(ownerId));
        return ownerProperties;
    }

    public Integer findOwnerIdByEmail(String email) {
        Owner owner = ownerRepository.findByEmail(email);
        return owner != null ? owner.getUserId() : null;
    }

    public List<RentalApplication> getOwnerRentalApplications(int ownerId) {
        return new ArrayList<>(rentalApplicationRepository.findByOwnerId(ownerId));
    }

    @Transactional
    public void save(Owner newowner){
        ownerRepository.saveOwnerCustom(newowner.getUserId(), newowner.getFirstName(), newowner.getLastName(), newowner.getTelephoneNumber());
    }
}
