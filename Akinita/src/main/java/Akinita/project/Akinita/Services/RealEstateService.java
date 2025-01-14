package Akinita.project.Akinita.Services;

import Akinita.project.Akinita.Repositories.RealEstate.CommercialPropertyRepository;
import Akinita.project.Akinita.Repositories.RealEstate.HouseRepository;
import Akinita.project.Akinita.Repositories.RealEstate.LandRepository;
import Akinita.project.Akinita.Repositories.RealEstate.ParkingRepository;
import org.springframework.stereotype.Service;

@Service
public class RealEstateService {

    private HouseRepository houseRepository;
    private CommercialPropertyRepository commercialPropertyRepository;
    private LandRepository landRepository;
    private ParkingRepository parkingRepository;


}
