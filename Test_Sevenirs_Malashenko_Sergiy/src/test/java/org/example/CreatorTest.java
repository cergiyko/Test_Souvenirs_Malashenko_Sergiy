package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreatorTest {
    Creator creator;

    @BeforeEach
    void setUp() throws IOException, ParseException {
        creator = new Creator();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createManufacturer() {
        int expected = 7;
        creator.createManufacturer("Omega","China","IBAN 1058");
        creator.createManufacturer("Epson","China","IBAN 1089");
        int result = creator.manufacturerSet.size();
        assertEquals(expected,result);    }

    @Test
    void createSouvenir() throws ParseException {
        int expected = 10;
        creator.createSouvenir("pen","Google", "12-10-2022","40");
        creator.createSouvenir("pen","Apple", "12-10-2010","20");
        int result = creator.souvenirList.size();
        assertEquals(expected,result);
    }

    @Test
    void manufacturerUpdate() {
        creator.createManufacturer("HP","China","IBAN 1150");
        creator.manufacturerUpdate("HP","Acer","China","IBAN 1565");
        Manufacturer exp = new Manufacturer("Acer","China","IBAN 1565");
        Manufacturer man = creator.manufacturerSet.stream()
                .filter(x->x.getName().equalsIgnoreCase("Acer"))
                .findFirst()
                .get();
        assertEquals(exp,man);
         }

    @Test
    void souvenirUpdate() throws ParseException {
        creator.createSouvenir("candle","Google", "12-05-2012","20");
        creator.souvenirUpdate(creator.souvenirList
                .stream()
                .filter(x->x.getName().equalsIgnoreCase("candle"))
                .findFirst()
                .get(),"umbrella","Sony", "11-12-2018",25);
        Souvenir exp = new Souvenir("umbrella",creator.manufacturerSet.stream()
                .filter(x->x.getName().equalsIgnoreCase("Sony"))
                .findFirst().get(), new SimpleDateFormat("dd-MM-yyyy").parse("11-12-2018"),25);
        Souvenir res = creator.souvenirList.stream()
                .filter(x->x.getName().equalsIgnoreCase("umbrella"))
                        .findFirst()
                                .get();
        assertEquals(exp.toString(),res.toString());
    }

    @Test
    void souvenirsFromAGivenManufacturer() {
        String result = creator.souvenirsFromAGivenManufacturer("Samsung").toString();
        String expected="[pen//Samsung//05-08-2012//70, magnet//Samsung//05-08-2015//70]";
        assertEquals(expected,result);
    }

    @Test
    void souvenirsProducedInAGivenCountry() {
        String result = creator.souvenirsProducedInAGivenCountry("Japan").toString();
        String expected="[pen//Sony//05-08-2012//70]";
        assertEquals(expected,result);
    }

    @Test
    void manufacturersWithLowerPrices() {
        String result = creator.manufacturersWithLowerPrices(80).toString();
        String expected="[Samsung//Korea//IBAN 1040, " +
                "Siemens//Germany//IBAN 1060, " +
                "Sony//Japan//IBAN 1050, " +
                "Apple//USA//IBAN 1030]";
        assertEquals(expected,result);
    }

    @Test
    void manufacturersAndTheirSouvenirs() {
    String result = creator.manufacturersAndTheirSouvenirs().toString();
        String expected = "{Siemens//Germany//IBAN 1060=" +
                "[pen//Siemens//05-08-2012//70]," +
                " Samsung//Korea//IBAN 1040=" +
                "[pen//Samsung//05-08-2012//70," +
                " magnet//Samsung//05-08-2015//70]," +
                " Sony//Japan//IBAN 1050=" +
                "[pen//Sony//05-08-2012//70], " +
                "Google//USA//IBAN 1020=" +
                "[pen//Google//03-05-2011//80, " +
                "magnet//Google//03-05-2014//80], " +
                "Apple//USA//IBAN 1030=" +
                "[pen//Apple//02-03-2010//50, " +
                "magnet//Apple//02-03-2013//50]}";
        assertEquals(expected,result);
    }

    @Test
    void souvenirManufacturerInAGivenYear() {
        String result = creator.souvenirManufacturerInAGivenYear("pen",2012).toString();
        String expected="[Samsung//Korea//IBAN 1040, Sony//Japan//IBAN 1050, Siemens//Germany//IBAN 1060]";
        assertEquals(expected,result);
    }

    @Test
    void souvenirByYear() {
        String result = creator.souvenirByYear().toString();
        String expected="{2010=" +
                "[pen//Apple//02-03-2010//50]," +
                " 2011=" +
                "[pen//Google//03-05-2011//80]," +
                " 2012=" +
                "[pen//Samsung//05-08-2012//70," +
                " pen//Sony//05-08-2012//70," +
                " pen//Siemens//05-08-2012//70]," +
                " 2013=" +
                "[magnet//Apple//02-03-2013//50]," +
                " 2014=" +
                "[magnet//Google//03-05-2014//80]," +
                " 2015=" +
                "[magnet//Samsung//05-08-2015//70]}";
        assertEquals(expected,result);
    }

    @Test
    void deleteManufacturerAndSouvenirs() {
        creator.deleteManufacturerAndSouvenirs("Google");
        int resMan = creator.manufacturerSet.size();
        int resSuov = creator.souvenirList.size();
        int expMan = 4;
        int expSouv = 6;
        assertEquals(expMan,resMan);
        assertEquals(expSouv,resSuov);
    }
}