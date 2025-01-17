package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Renter;
import Akinita.project.Akinita.Entities.Role;
import Akinita.project.Akinita.Repositories.RealEstate.CommercialPropertyRepository;
import Akinita.project.Akinita.Repositories.RealEstate.HouseRepository;
import Akinita.project.Akinita.Repositories.RealEstate.LandRepository;
import Akinita.project.Akinita.Repositories.RealEstate.ParkingRepository;
import Akinita.project.Akinita.Repositories.RentalApplicationRepository;
import Akinita.project.Akinita.Repositories.User.RenterRepository;
import Akinita.project.Akinita.Repositories.User.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RenterService {

    private final HouseRepository houseRepository;

    private final LandRepository landRepository;

    private final ParkingRepository parkingRepository;

    private final CommercialPropertyRepository commercialPropertyRepository;

    private final RenterRepository renterRepository;

    private final RoleRepository roleRepository;

    private final RentalApplicationRepository rentalApplicationRepository;

    @Autowired
    public RenterService(HouseRepository houseRepository, LandRepository landRepository, ParkingRepository parkingRepository,
                         CommercialPropertyRepository commercialPropertyRepository, RenterRepository renterRepository,
                         RoleRepository roleRepository, RentalApplicationRepository rentalApplicationRepository) {
        this.houseRepository = houseRepository;
        this.landRepository = landRepository;
        this.parkingRepository = parkingRepository;
        this.commercialPropertyRepository = commercialPropertyRepository;
        this.renterRepository = renterRepository;
        this.roleRepository = roleRepository;
        this.rentalApplicationRepository = rentalApplicationRepository;
    }

    @Transactional
    public void saveRenter(Integer userId, Renter renter) {

        renter.setId(userId);

        Role role = roleRepository.findByName("ROLE_RENTER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        renter.setRoles(roles);

        renterRepository.save(renter);
    }
}
