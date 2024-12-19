package Akinita.project.Akinita.entities;

public class RealEstate {

    protected int id;
    protected String estateName;
    protected String location;
    protected int price;
    protected int constructionDate;
    protected String description;

    public RealEstate(int id, String estateName, String location, int price, int constructionDate, String description) {
        this.id = id;
        this.estateName = estateName;
        this.location = location;
        this.price = price;
        this.constructionDate = constructionDate;
        this.description = description;
    }
}
