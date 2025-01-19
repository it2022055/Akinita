package Akinita.project.Akinita.Interfaces.LimitedMethods;


import jakarta.validation.constraints.NotBlank;

public interface BuildingFees {

    // Building Fees
    @NotBlank(message = "Building fees are required") Boolean getBuildingFees();
    void setBuildingFees(boolean buildingFees);

}
