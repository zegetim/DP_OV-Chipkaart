package dao;

import domain.OVChipkaart;
import domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoPsql implements ProductDao{
    private Connection connection;

    public ProductDaoPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO product (product_nummer, naam, beschrijving, prijs) " + "VALUES (?,?,?,?)");
        preparedStatement.setInt(1,product.getProductnummer());
        preparedStatement.setString(2,product.getNaam());
        preparedStatement.setString(3,product.getBeschrijving());
        preparedStatement.setDouble(4,product.getPrijs());
        preparedStatement.execute();

        for(OVChipkaart ovChipkaart : product.getOvchipkaarten()){
            preparedStatement = this.connection.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer,product_nummer) VALUES (?,?)");
            preparedStatement.setInt(1, ovChipkaart.getKaartnummer());
            preparedStatement.setInt(2, product.getProductnummer());
            preparedStatement.execute();
        }
        preparedStatement.close();
        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE product SET naam = '" + product.getNaam() + "', " +
                    "beschrijving = '" + product.getBeschrijving() + "', " +
                    "prijs = '" + product.getPrijs() + "' " +
                    "WHERE product_nummer = " + product.getProductnummer());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM ov_chipkaart_product WHERE product_nummer = " + product.getProductnummer() + ";");
            statement.executeUpdate("DELETE FROM product WHERE product_nummer = " + product.getProductnummer() + ";");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        List<Product> producten = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT product.product_nummer, naam, beschrijving, prijs FROM product JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer WHERE ov_chipkaart_product.kaart_nummer = " + ovChipkaart.getKaartnummer() + ";");
        if(resultset.next()) {
            Product product = new Product(resultset.getInt("product_nummer"), resultset.getString("naam"), resultset.getString("beschrijving"), resultset.getDouble("prijs"));
            producten.add(product);
        }
        return producten;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> producten = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT product.product_nummer, product.naam, product.beschrijving, product.prijs, ov_chipkaart.kaart_nummer, ov_chipkaart.geldig_tot, ov_chipkaart.klasse, ov_chipkaart.saldo FROM product " +
                "INNER JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                "INNER JOIN ov_chipkaart ON ov_chipkaart.kaart_nummer = ov_chipkaart_product.kaart_nummer");
        while(resultset.next()) {
            Product product = new Product(resultset.getInt("product_nummer"), resultset.getString("naam"), resultset.getString("beschrijving"), resultset.getDouble("prijs"));
            OVChipkaart ovChipkaart = new OVChipkaart(resultset.getInt("kaart_nummer"), resultset.getDate("geldig_tot"), resultset.getInt("klasse"), resultset.getDouble("saldo"));
            product.addOvchipkaart(ovChipkaart);
            producten.add(product);
            }
        statement.close();
        resultset.close();
        return producten;
    }
}
