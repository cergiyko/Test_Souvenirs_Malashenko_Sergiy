package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Creator {
    List<String> manufacturerListStr = Files.readAllLines(Path.of("ManufacturersList.txt"));
    Set<Manufacturer> manufacturerSet = makeSetManufacturers(manufacturerListStr);
    List<String> souvenirListStr = Files.readAllLines(Path.of("SevenirList.txt"));
    List<Souvenir> souvenirList = makeListSouvenirs(souvenirListStr, manufacturerSet);


    public Creator() throws IOException, ParseException {
    }
    //методы для создания новых объектов:
public void createManufacturer(String name, String country, String requisites){
    manufacturerSet.add(new Manufacturer(name, country, requisites));
    }
    public void createSouvenir(String name, String manufacturer, String date, String price) throws ParseException {
        Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        souvenirList.add(new Souvenir(name, manufacturerSet
                .stream()
                .filter(m ->manufacturer.equalsIgnoreCase(m.getName())) //Objects.equals(m.getName(), manufacturer))
                .findFirst().get(), date1, Integer.parseInt(price)));
        }
    //Метод принимает список строк и возвращает Set производителей
        Set<Manufacturer> makeSetManufacturers(List<String> lines){
        Set<Manufacturer> manufacturerList = new HashSet<>();
        for (String line : lines ) {
            List<String> field = new ArrayList<>();
            field.addAll(Arrays.asList(line.split("//")));
            manufacturerList.add(new Manufacturer(field.get(0),field.get(1),field.get(2)));
       }return manufacturerList;
    }
    //Метод принимает список строк и Set производителей,
    //делает проверку, чтобы указаный производитель был в этом Set
    //и возвращает List сувениров
    List<Souvenir> makeListSouvenirs(List<String> lines, Set<Manufacturer> setManufactures) throws ParseException {
        List<Souvenir> SouvenirsList = new ArrayList<>();

        for (String line : lines ) {
            List<String> field = new ArrayList<>();
            field.addAll(Arrays.asList(line.split("//")));

            Manufacturer manufacturer = setManufactures
                        .stream()
                        .filter(m -> Objects.equals(m.getName(), field.get(1)))
                        .findFirst().get();
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(field.get(2));
            int price = Integer.parseInt(field.get(3));
         SouvenirsList.add(new Souvenir(field.get(0),manufacturer,date,price));
        }return SouvenirsList;
    }
    String allManufacturersToPrint(){
        String str =  manufacturerSet.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
    return str;}
       String allSouvenirsToPrint(){
        String str = souvenirList.stream()
                .map(String::valueOf)
                .distinct()
                .collect(Collectors.joining("\n"));
        return str;
    }
    //Методы для редактирования объектов:
    public void manufacturerUpdate(String name,String updateName, String updateCountry, String updateRequisites){
  manufacturerSet.stream()
          .filter(m->m.getName().equalsIgnoreCase(name))
          .forEach(x->{x.setName(updateName);
                        x.setCountry(updateCountry);
                        x.setRequisites(updateRequisites);});}
    public void souvenirUpdate(Souvenir souvenir,String updateName,
                               String updateManufacturer, String updateDate, int updatePrice) throws ParseException {
        souvenir.setName(updateName);
    //    if(souvenir.g)
        souvenir.setManufacturer(manufacturerSet.stream()
                .filter(x->x.getName().equalsIgnoreCase(updateManufacturer))
                .findFirst()
                .get());
        souvenir.setReleaseDate(new SimpleDateFormat("dd-MM-yyyy").parse(updateDate));
        souvenir.setPrice(updatePrice);
    }
    //метод для вывода информации о сувенирах заданного производителя:
    public List<Souvenir> souvenirsFromAGivenManufacturer(String manufacturerName){
        System.out.println(manufacturerName + " souvenirs:");
        List<Souvenir> list = souvenirList.stream()
                .filter(s -> manufacturerName.equalsIgnoreCase(s.getManufacturer().getName()))
                .toList();

                list.forEach(System.out::println);
        System.out.println("--------------------");
        return list;
    }
    //метод для вывода информации о сувенирах, произведенных в заданной стране:
    public List<Souvenir> souvenirsProducedInAGivenCountry(String country){
        System.out.println("Souvenirs from the " + country);
            List <Souvenir> list = souvenirList.stream()
                .filter(s->country.equalsIgnoreCase(s.getManufacturer().getCountry()))
                .toList();
                list.forEach(System.out::println);
        System.out.println("--------------------");
        return list;
    }
    //метод для вывода информации о производителях, чьи цены на сувениры
    //меньше заданной.
    public Set<Manufacturer> manufacturersWithLowerPrices(int price){
           Set<Manufacturer> setMan = new HashSet<>();
           souvenirList.stream()
                    .filter(s->s.getPrice()<price)
                    .forEach(s-> setMan.add(s.getManufacturer()));
        System.out.println("Manufacturers with lower prices: ");
        setMan.stream().forEach(System.out::println);
        System.out.println("--------------------");
    return setMan;}

        //метод для вывода информации о всех производителям и, для каждого производителя
        //информацию о всех сувенирах, которые он производит:
    public Map<Manufacturer, List<Souvenir>> manufacturersAndTheirSouvenirs(){
        Map<Manufacturer, List<Souvenir>> map = souvenirList.stream()
                .collect(Collectors.groupingBy(Souvenir::getManufacturer));
        map.entrySet().stream().forEach(x-> {System.out.println(x.getKey());
                                            x.getValue()
                                            .stream()
                                            .forEach(System.out::println);
                                            System.out.println();});
        System.out.println("--------------------");
    return map;}

    //метод для вывода информации о производителях заданного сувенира,
    // произведенного в заданном году.
    public List<Manufacturer> souvenirManufacturerInAGivenYear(String souvenir, int year){
             System.out.println(souvenir + " in " + year + " produced:");
        List<Manufacturer> list = souvenirList.stream()
                .filter(s->s.getName().equalsIgnoreCase(souvenir)
                       &&s.getReleaseDate().getYear()==year-1900)
                .map(Souvenir::getManufacturer)
                .distinct()
                .toList();
                list.forEach(System.out::println);
        System.out.println("--------------------");
         return list;
    }
    //Для каждого года вывести список сувениров, произведенных в этом году
    public Map<Integer, List<Souvenir>> souvenirByYear(){
        Map<Integer, List<Souvenir>> map = souvenirList.stream()
                .collect(Collectors.groupingBy(Souvenir::getYear));
        map.entrySet().stream().forEach(x-> {System.out.println(x.getKey());
                    x.getValue()
                    .stream()
                    .forEach(System.out::println);});
        System.out.println("--------------------");
        return map;
    }
    // Удалить заданного производителя и его сувениры
    public void deleteManufacturerAndSouvenirs(String manufacturer){
        souvenirList.removeIf(x->x.getManufacturer().getName().equalsIgnoreCase(manufacturer));
        manufacturerSet.removeIf(x->x.getName().equalsIgnoreCase(manufacturer));
        System.out.println(souvenirList);
        System.out.println(manufacturerSet);
    }
    }














