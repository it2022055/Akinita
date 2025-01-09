package Akinita.project.Akinita.entities;

import Akinita.project.Akinita.Interfaces.RealEstate;

import java.util.Date;

public class House implements RealEstate {

    private String[][] facilities;
    private boolean buildingFees;

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public String getEstateName() {
        return "";
    }

    @Override
    public void setEstateName(String estateName) {

    }

    @Override
    public String getLocation() {
        return "";
    }

    @Override
    public void setLocation(String location) {

    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void setPrice(int price) {

    }

    @Override
    public Date getConstructionDate() {
        return null;
    }

    @Override
    public void setConstructionDate(Date constructionDate) {

    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void setDescription(String description) {

    }
}
