package org.example;

import java.io.*;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        new Main().ran();}
    private void ran () throws IOException, ParseException {
      Creator creator = new Creator();
        creator.createManufacturer("Genius","China","IBAN 1060");
        creator.createSouvenir("trinketsss", "Genius","10-20-2010","80");
        creator.souvenirUpdate(creator.souvenirList
                        .stream()
                        .filter(x->x.getName().equalsIgnoreCase("trinketsss"))
                        .findFirst()
                        .get(),"trinket",
                "Genius","10-20-2010",40);
        creator.manufacturerUpdate("Genius", "Genius", "Korea", "IBAN 2255");
        creator.souvenirsFromAGivenManufacturer("Google");
        creator.souvenirsProducedInAGivenCountry("USA");
        creator.manufacturersWithLowerPrices(80);
        creator.manufacturersAndTheirSouvenirs();
        creator.souvenirManufacturerInAGivenYear("pen",2012);
        creator.souvenirByYear();
        creator.deleteManufacturerAndSouvenirs("Genius");
        System.out.println("Manuracrurers: " + "\n" +creator.allManufacturersToPrint());
        System.out.println("Sevenirs: " + "\n" + creator.allSouvenirsToPrint());
        writer(creator.allManufacturersToPrint(), "ManufacturersList.txt");
        writer(creator.allSouvenirsToPrint(), "SevenirList.txt");
    }
    private static void writer(String str, String file){
        try(PrintWriter out = new PrintWriter(file)){
            out.println(str);}
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
