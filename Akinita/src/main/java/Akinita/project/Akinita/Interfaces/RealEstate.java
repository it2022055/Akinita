package Akinita.project.Akinita.Interfaces;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

public interface RealEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int getId();

    void setId(int id);

    @NotBlank(message = "Title is required")
    @Size(max = 50)
    String getEstateName();

    void setEstateName(@NotBlank(message = "Title is required") @Size(max = 50) String estateName);

    @NotBlank(message = "Location is required")
    @Size(max = 50)
    String getLocation();

    void setLocation(@NotBlank(message = "Location is required") @Size(max = 50) String location);

    @NotBlank(message = "Price is required")
    @Size(max = 10)
    int getPrice();

    void setPrice(@NotBlank(message = "Price is required") @Size(max = 10) int price);

    @NotBlank(message = "Description is required")
    String getDescription();

    void setDescription(@NotBlank(message = "Description is required") String description);
}
