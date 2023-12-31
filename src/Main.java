import dao.*;
import domain.Adres;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/ovchip","postgres", "test");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from reiziger");
        System.out.println("Alle reizigers:");
        while (resultSet.next()){
            System.out.println(resultSet.getString("achternaam") + " " + resultSet.getString("voorletters") + " (" + resultSet.getDate("geboortedatum") + ")");
        }
        ProductDaoPsql productDaoPsql = new ProductDaoPsql(connection);
        ReizigerDaoPsql rdao = new ReizigerDaoPsql(connection);
        AdresDaoPsql adresDaoPsql = new AdresDaoPsql(connection);
        OVChipkaartDaoPsql ovdao = new OVChipkaartDaoPsql(connection);

        testReizigerDAO(rdao);
        testAdresDao(adresDaoPsql);
        testOVChipDAO(ovdao);
        testProductOvchip(productDaoPsql,ovdao);
        statement.close();
        resultSet.close();
    }
    /**
     * P2. Domain.Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Domain.Reiziger DAO
     *
     * @throws SQLException
     */

    private static void testReizigerDAO(ReizigerDao rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(179, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // update en findbygbdatum
        String gbdatumupdate = "1991-03-14";
        Reiziger sietskeupdate = new Reiziger(179, "T", "", "Boers", java.sql.Date.valueOf(gbdatumupdate));
        rdao.update(sietskeupdate);
        reizigers = rdao.findByGbdatum(gbdatumupdate);
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();


    }

    private static void testAdresDao(AdresDao adresDao) throws SQLException {

        // save adres en findall
        Adres adres = new Adres(157,"3500AA",1,"dorpstraat","Utrecht", 179);
        adresDao.save(adres);
        List<Adres> adressen = adresDao.findAll();
        System.out.println(adressen);

        // findByReiziger
        String gbdatum = "1981-03-14";
        Adres adres1 = adresDao.findByReiziger(new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum)));
        System.out.println(adres1);
    }

    private static void testOVChipDAO(OVChipkaartDao ovdao) throws SQLException {
        String datum = "2025-01-01";
        String gbdatum = "2001-07-28";
        OVChipkaart ovChipkaart = new OVChipkaart(1124,java.sql.Date.valueOf(datum),2,10);
        Reiziger reiziger = new Reiziger(177, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        ovChipkaart.setReiziger(reiziger);
        ovdao.save(ovChipkaart);
        List<OVChipkaart> ovChipkaartList = ovdao.findAll();
        for(OVChipkaart ovChip : ovChipkaartList){
            System.out.println(ovChip);
        }

    }

    private static void testProductOvchip(ProductDao productDao, OVChipkaartDao ovChipkaartDao) throws SQLException {
        String gbdatum = "1990-01-01";
        String datum = "2025-01-01";
        Reiziger reiziger = new Reiziger(77,"t", "", "test" ,java.sql.Date.valueOf(gbdatum));
        OVChipkaart ovChipkaart = new OVChipkaart(142,java.sql.Date.valueOf(datum),2,10, reiziger);
        ovChipkaartDao.save(ovChipkaart);
        Product product = new Product(182, "testProduct", "Een product om de opdracht te testen", 0.80);
        product.addOvchipkaart(ovChipkaart);
        productDao.save(product);

        List<Product> productList = productDao.findByOVChipkaart(ovChipkaart);
        for(Product p : productList){
            System.out.println("Product bij OVChipkaart: " + p.toString());
        }

        Product product2 = new Product(182, "testProduct", "Een product om de opdracht te testen", 0.80);
        OVChipkaart ovChipkaart2 = new OVChipkaart(182,java.sql.Date.valueOf(datum),1,8);
        Reiziger reiziger2 = new Reiziger(110,"T","","Zegeling",java.sql.Date.valueOf(gbdatum));
        ovChipkaart2.setReiziger(reiziger2);
        ovChipkaart2.addProduct(product2);
        product2.addOvchipkaart(ovChipkaart2);
        ovChipkaartDao.update(ovChipkaart2);

        List<Product> productList2 = productDao.findAll();
        System.out.println("Producten:");
        for(Product p : productList2){
            System.out.println(p.toString());
        }
    }
}
