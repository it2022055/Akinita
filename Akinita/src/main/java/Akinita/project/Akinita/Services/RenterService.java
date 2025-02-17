package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Entities.Actors.Renter;
import Akinita.project.Akinita.Repositories.User.RenterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class RenterService {

    @Autowired
    private RenterRepository renterRepository;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private PropertyService propertyService;

    public Integer findRenterIdByEmail(String email) {
        Renter renter = renterRepository.findByEmail(email);
        return renter != null ? renter.getUserId() : null;
    }

    public void saveApplication(RentalApplication rentalApplication) {
        applicationService.save(rentalApplication);
    }

    public Renter getRenterById(Integer renterId) {
        return renterRepository.findByUserId(renterId);
    }

    @Transactional
    public void save(Renter newRenter){
        renterRepository.saveRenterCustom(newRenter.getUserId(), newRenter.getFirstName(), newRenter.getLastName(), newRenter.getTelephoneNumber(), newRenter.getAcceptance());
    }

    public List<Renter> findAllUnacceptedRenters(){ //Μέθοδος επιστροφής μη αποδεχτών Renters
        return renterRepository.findAllUnacceptedRenters();
    }

    @Transactional
    public void UpdateRenter(Renter renter) {renterRepository.save(renter);} //Μέθοδος ενημέρωσης Renters

    public boolean existsTelephone(String telephone) {
        return renterRepository.existsByTelephoneNumber(telephone);
    }

    public void deleteRenter(int id){

        // Διαγραφή των αιτήσεων ενοικίασης από τον renter με βάση το id
        applicationService.deleteByRenterId(id);
        // Διαγραφή του renter
        renterRepository.deleteById(id);
        List<Property> rentedProperties = propertyService.findPropertiesByRenterId(id);
        for (Property property : rentedProperties) {
            property.setRenter(null);
            propertyService.updateProperty(property);
        }
    }
}
