package Akinita.project.Akinita.entities;

public class House extends RealEstate {

    private String[][] facilities;
    private boolean buildingFees;

    public House(int id, String estateName, String location, int price, int constructionDate, String description) {
        super(id, estateName, location, price, constructionDate, description);
    }
}
