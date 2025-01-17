package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Entities.Role;
import Akinita.project.Akinita.Interfaces.RealEstate;
import Akinita.project.Akinita.Repositories.RentalApplicationRepository;
import Akinita.project.Akinita.Repositories.User.OwnerRepository;
import Akinita.project.Akinita.Repositories.RealEstate.*;
import Akinita.project.Akinita.Entities.Owner;
import Akinita.project.Akinita.Entities.RentalApplication;
import Akinita.project.Akinita.Repositories.User.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OwnerService {

    private final HouseRepository houseRepository;

    private final LandRepository landRepository;

    private final ParkingRepository parkingRepository;

    private final CommercialPropertyRepository commercialPropertyRepository;

    private final OwnerRepository ownerRepository;

    private final RoleRepository roleRepository;

    private final RentalApplicationRepository rentalApplicationRepository;

    @Autowired
    public OwnerService(HouseRepository houseRepository, LandRepository landRepository, ParkingRepository parkingRepository,
                        CommercialPropertyRepository commercialPropertyRepository, OwnerRepository ownerRepository,
                        RoleRepository roleRepository, RentalApplicationRepository rentalApplicationRepository) {
        this.houseRepository = houseRepository;
        this.landRepository = landRepository;
        this.parkingRepository = parkingRepository;
        this.commercialPropertyRepository = commercialPropertyRepository;
        this.ownerRepository = ownerRepository;
        this.roleRepository = roleRepository;
        this.rentalApplicationRepository = rentalApplicationRepository;
    }

    @Transactional
    public void saveOwner(Integer userId, Owner owner) {

        owner.setId(userId);

        Role role = roleRepository.findByName("ROLE_OWNER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        owner.setRoles(roles);
        owner.setId(userId);

        ownerRepository.save(owner);
    }

    // Μέθοδος που επιστρέφει τα ακίνητα του ιδιοκτήτη
    public List<RealEstate> getOwnerProperties(int ownerId) {
        List<RealEstate> ownerProperties = new ArrayList<>();

//         Ανάκτηση ακινήτων για κάθε τύπο
        ownerProperties.addAll(houseRepository.findByOwnerId(ownerId));
        ownerProperties.addAll(landRepository.findByOwnerId(ownerId));
        ownerProperties.addAll(parkingRepository.findByOwnerId(ownerId));
        ownerProperties.addAll(commercialPropertyRepository.findByOwnerId(ownerId));
        return ownerProperties;
    }



    public List<RentalApplication> getOwnerRentalApplications(int ownerId) {
        List<RentalApplication> ownerRentalApplications = new ArrayList<>();
        ownerRentalApplications.addAll(rentalApplicationRepository.findByOwnerId(ownerId));
        return ownerRentalApplications;
    }
}
