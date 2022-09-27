package org.example;

import java.util.Objects;

public class Manufacturer {
    private String name;
    private String country;
    private String requisites;

    public Manufacturer(String name, String country, String requisites) {
        this.name = name;
        this.country = country;
        this.requisites = requisites;
    }

    public String getRequisites() {
        return requisites;
    }

    public void setRequisites(String requisites) {
        this.requisites = requisites;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return name.equals(that.name) && country.equals(that.country) && requisites.equals(that.requisites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, requisites);
    }

    @Override
    public String toString() {
        return name +"//"+ country +"//"+ requisites ;

    }

}
