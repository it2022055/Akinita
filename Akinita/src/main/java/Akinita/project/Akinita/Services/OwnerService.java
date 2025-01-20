package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Repositories.RealEstate.CommercialPropertyGenericRepository;
import Akinita.project.Akinita.Repositories.RealEstate.HouseGenericRepository;
import Akinita.project.Akinita.Repositories.RealEstate.LandGenericRepository;
import Akinita.project.Akinita.Repositories.RealEstate.ParkingGenericRepository;
import Akinita.project.Akinita.Repositories.User.OwnerRepository;
import Akinita.project.Akinita.Entities.Actors.Owner;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private HouseGenericRepository houseRepository;
    @Autowired
    private LandGenericRepository landRepository;
    @Autowired
    private ParkingGenericRepository parkingRepository;
    @Autowired
    private CommercialPropertyGenericRepository commercialPropertyRepository;
   @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private ApplicationService applicationService;

    // Μέθοδος που επιστρέφει τα ακίνητα του ιδιοκτήτη
    public List<RealEstate> getOwnerProperties(int ownerId) {
        List<RealEstate> ownerProperties = new ArrayList<>();

        //Ανάκτηση ακινήτων για κάθε τύπο
        ownerProperties.addAll(houseRepository.findByVisibilityAndOwner_UserId("Visible",ownerId));
        ownerProperties.addAll(landRepository.findByVisibilityAndOwner_UserId("Visible", ownerId));
        ownerProperties.addAll(parkingRepository.findByVisibilityAndOwner_UserId("Visible", ownerId));
        ownerProperties.addAll(commercialPropertyRepository.findByVisibilityAndOwner_UserId("Visible",ownerId));
        return ownerProperties;
    }

    public Integer findOwnerIdByEmail(String email) {
        Owner owner = ownerRepository.findByEmail(email);
        return owner != null ? owner.getUserId() : null;
    }

    public Owner findById(int ownerId) {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        return optionalOwner.orElse(null);
    }

    public List<RentalApplication> getOwnerRentalApplications(int ownerId) {
        return new ArrayList<>(applicationService.findByOwner(ownerId));
    }

    public Owner getOwner(int id) {
        return ownerRepository.findByOwnerId(id);
    }

    @Transactional
    public void save(Owner newowner){
        ownerRepository.saveOwnerCustom(newowner.getUserId(), newowner.getFirstName(), newowner.getLastName(), newowner.getTelephoneNumber());
    }
}
