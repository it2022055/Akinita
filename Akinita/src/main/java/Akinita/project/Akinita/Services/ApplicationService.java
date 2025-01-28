package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Properties.Property;
import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Repositories.RentalApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
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

    public void deleteApplication(int property_id) {
         rentalApplicationRepository.deleteByPropertyId(property_id);
    }

    public RentalApplication save(RentalApplication rentalApplication) {
        return rentalApplicationRepository.save(rentalApplication);
    }

    public void setDateCurrDate(int applicationId) {
        Date currDate = new Date();
        rentalApplicationRepository.setDateCurrDate(applicationId, currDate);
    }

    public List<RentalApplication> findAllApps(Integer renterId){
        return rentalApplicationRepository.findAllApps(renterId);
    }

    public RentalApplication findById(int applicationId) {
        return rentalApplicationRepository.findById(applicationId);
    }

    public void acceptApplication(int appId){
        rentalApplicationRepository.setStatus(true, appId);
    }

    public void declineApplication(int appId) {
        RentalApplication rentalApplication = rentalApplicationRepository.findById(appId);

        rentalApplication.setStatus(false);

        rentalApplicationRepository.save(rentalApplication);
    }

    public void setApplicationStatus(int applicationId, Boolean status) {
        RentalApplication rentalApplication = rentalApplicationRepository.findById(applicationId);
        rentalApplication.setStatus(status);
        rentalApplicationRepository.save(rentalApplication);
    }
}
