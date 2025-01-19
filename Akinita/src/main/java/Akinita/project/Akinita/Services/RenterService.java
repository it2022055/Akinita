package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Entities.Actors.Renter;
import Akinita.project.Akinita.Repositories.User.RenterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RenterService {

    @Autowired
    private RenterRepository renterRepository;
    @Autowired
    private ApplicationService applicationService;

    public Integer findRenterIdByEmail(String email) {
        Renter renter = renterRepository.findByEmail(email);
        return renter != null ? renter.getUserId() : null;
    }

    public List<RentalApplication> getRenterRentalApplications(int renterId) {
        return new ArrayList<>(applicationService.findByRenter(renterId));
    }

    public RentalApplication saveApplication(RentalApplication rentalApplication) {
        return applicationService.save(rentalApplication);
    }

    public Renter getRenterById(Integer renterId) {
        return renterRepository.findByUserId(renterId);
    }

    @Transactional
    public void save(Renter newRenter){
        renterRepository.saveRenterCustom(newRenter.getUserId(), newRenter.getFirstName(), newRenter.getLastName(), newRenter.getTelephoneNumber());
    }
}
