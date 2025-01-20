package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Repositories.RentalApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private RentalApplicationRepository rentalApplicationRepository;

    public List<RentalApplication> findByOwner(int owner_id) {
        return rentalApplicationRepository.findByOwnerId(owner_id);
    }

    public List<RentalApplication> findByRenter(int renter_id) {
        return rentalApplicationRepository.findByRenterId(renter_id);
    }

    public List<RentalApplication> findByProperty(int property_id) {
        return rentalApplicationRepository.findByPropertyId(property_id);
    }

    public RentalApplication save(RentalApplication rentalApplication) {
        return rentalApplicationRepository.save(rentalApplication);
    }

    public RentalApplication getApplication(int rpId) {
        return  rentalApplicationRepository.findById(rpId);
    }

    public void SetRentalApplicationStatus(Boolean status) {rentalApplicationRepository.setStatus(status);}
}
