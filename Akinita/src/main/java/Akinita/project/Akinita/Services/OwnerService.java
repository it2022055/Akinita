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

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class OwnerService {

    @Autowired
    private HouseGenericRepository houseRepository; //Δήλωση του house Repository
    @Autowired
    private LandGenericRepository landRepository; //Δήλωση του land repository
    @Autowired
    private ParkingGenericRepository parkingRepository; //Δήλωση του parking repository
    @Autowired
    private CommercialPropertyGenericRepository commercialPropertyRepository; //Δήλωση του commercial property repository
   @Autowired
    private OwnerRepository ownerRepository; //Δήλωση του owner repository
    @Autowired
    private ApplicationService applicationService; //Δήλωση του application service


    public List<RealEstate> getOwnerProperties(int ownerId) { //Μέθοδος που επιστρέφει τα ακίνητα του ιδιοκτήτη
        List<RealEstate> ownerProperties = new ArrayList<>();

        //Ανάκτηση ακινήτων για κάθε τύπο
        ownerProperties.addAll(houseRepository.findByVisibilityAndOwner_UserId("Visible",ownerId));
        ownerProperties.addAll(landRepository.findByVisibilityAndOwner_UserId("Visible", ownerId));
        ownerProperties.addAll(parkingRepository.findByVisibilityAndOwner_UserId("Visible", ownerId));
        ownerProperties.addAll(commercialPropertyRepository.findByVisibilityAndOwner_UserId("Visible",ownerId));
        return ownerProperties;
    }

    public Integer findOwnerIdByEmail(String email) { //Μέθοδος ανάκτησης ID από το email
        Owner owner = ownerRepository.findByEmail(email);
        return owner != null ? owner.getUserId() : null;
    }

    public Owner findById(int ownerId) { //Μέθοδος ανάκτησης Owner από το ID
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        return optionalOwner.orElse(null);
    }

    public List<RentalApplication> getOwnerRentalApplications(int ownerId) { //Μέθοδος επιστροφής των αιτήσεων ενοικίασης
        return new ArrayList<>(applicationService.findByOwner(ownerId));
    }

    public Owner getOwner(int id) {
        return ownerRepository.findByOwnerId(id);
    }

    @Transactional
    public void save(Owner newowner){ //Μέθοδος αποθήκευσης του Owner
        ownerRepository.saveOwnerCustom(newowner.getUserId(), newowner.getFirstName(), newowner.getLastName(), newowner.getTelephoneNumber());
    }
}
