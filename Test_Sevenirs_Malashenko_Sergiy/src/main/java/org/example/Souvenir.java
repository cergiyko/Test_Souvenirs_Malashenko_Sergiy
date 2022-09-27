package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Souvenir {
    private String name;
    private Manufacturer manufacturer;
    private Date releaseDate;
    private int price;

    public Souvenir(String name, Manufacturer manufacturer, Date releaseDate, int price) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.releaseDate = releaseDate;
        this.price = price;
    }
    public int getYear() {
        int year = releaseDate.getYear()+1900;
        return year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {

        this.releaseDate = releaseDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        return name +"//"+ manufacturer.getName() +"//"+formater.format(releaseDate)+"//"+price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Souvenir souvenir = (Souvenir) o;
        return price == souvenir.price && Objects.equals(name, souvenir.name) && Objects.equals(manufacturer, souvenir.manufacturer) && Objects.equals(releaseDate, souvenir.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, manufacturer, releaseDate, price);
    }
}
