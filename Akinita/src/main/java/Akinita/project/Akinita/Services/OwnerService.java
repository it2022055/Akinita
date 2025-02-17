package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Properties.CommercialProperty;
import Akinita.project.Akinita.Entities.Properties.House;
import Akinita.project.Akinita.Entities.Properties.Land;
import Akinita.project.Akinita.Entities.Properties.Parking;
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
    
    public List<RealEstate> getOwnerProperties(int ownerId) { //Μέθοδος που επιστρέφει τα ακίνητα του ιδιοκτήτη
        List<RealEstate> ownerProperties = new ArrayList<>();

        //Ανάκτηση ακινήτων για κάθε τύπο
        ownerProperties.addAll(houseRepository.findByVisibilityOrVisibilityAndOwner_UserId("Visible", "Occupied", ownerId));
        ownerProperties.addAll(landRepository.findByVisibilityOrVisibilityAndOwner_UserId("Visible", "Occupied", ownerId));
        ownerProperties.addAll(parkingRepository.findByVisibilityOrVisibilityAndOwner_UserId("Visible", "Occupied", ownerId));
        ownerProperties.addAll(commercialPropertyRepository.findByVisibilityOrVisibilityAndOwner_UserId("Visible", "Occupied", ownerId));

        return ownerProperties;
    }

    public Integer getOwnerIdByEmail(String email) { //Μέθοδος ανάκτησης ID από το email
        Owner owner = ownerRepository.findByEmail(email);
        return owner != null ? owner.getUserId() : null;
    }

    public Owner getOwnerById(int ownerId) { //Μέθοδος ανάκτησης Owner από το ID
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        return optionalOwner.orElse(null);
    }

    @Transactional
    public void save(Owner newowner){ //Μέθοδος αποθήκευσης του Owner
        ownerRepository.saveOwnerCustom(newowner.getUserId(), newowner.getFirstName(), newowner.getLastName(), newowner.getTelephoneNumber());
    }

    public boolean existsTelephone(String telephone) {
        return ownerRepository.existsByTelephoneNumber(telephone);
    }

    public void deleteOwner(int id) {
        // Διαγραφή από τον πίνακα house_facilities πριν από τη διαγραφή του House
        List<House> houses = houseRepository.findByOwnerId(id);
        if(!houses.isEmpty()) {
            for (House house : houses) {
                house.getFacilities().clear();  // Καθαρισμός της συλλογής facilities
                try{
                    houseRepository.delete(house);  // Διαγραφή του house
                }catch (Exception e){
                    throw  new RuntimeException(e.getMessage());
                }

            }
        }
        List<CommercialProperty> commercialProperties = commercialPropertyRepository.findByOwnerId(id);
        if(!commercialProperties.isEmpty()){
            for (CommercialProperty commercialProperty : commercialProperties) {
                commercialProperty.getFacilities().clear();
                try{
                    commercialPropertyRepository.delete(commercialProperty);
                }catch (Exception e){
                    throw  new RuntimeException(e.getMessage());
                }
            }
        }
        List<Land> land = landRepository.findByOwnerId(id);
        if(!land.isEmpty()){
            for (Land landt : land) {
                try{
                    landRepository.delete(landt);
                }catch (Exception e){
                    throw  new RuntimeException(e.getMessage());
                }
            }
        }

        List<Parking> parking = parkingRepository.findByOwnerId(id);
        if(!parking.isEmpty()){
            for (Parking parkingt : parking) {
                try{
                    parkingRepository.delete(parkingt);
                }catch (Exception e){
                    throw  new RuntimeException(e.getMessage());
                }
            }
        }
        ownerRepository.deleteById(id);
    }
}
