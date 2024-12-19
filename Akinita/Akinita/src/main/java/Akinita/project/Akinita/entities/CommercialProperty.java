package Akinita.project.Akinita.entities;

public class CommercialProperty extends RealEstate{

    protected String[][] facilities;
    protected boolean buildingFees;

    public CommercialProperty(int id, String estateName, String location, int price, int constructionDate, String description) {
        super(id, estateName, location, price, constructionDate, description);
    }
}
