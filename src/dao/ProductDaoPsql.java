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
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?");
            preparedStatement.setString(1,product.getNaam());
            preparedStatement.setString(2,product.getBeschrijving());
            preparedStatement.setDouble(3,product.getPrijs());
            preparedStatement.setInt(4,product.getProductnummer());
            preparedStatement.execute();

            for(OVChipkaart ovChipkaart : product.getOvchipkaarten()){
                preparedStatement = this.connection.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer,product_nummer) VALUES (?,?)");
                preparedStatement.setInt(1, ovChipkaart.getKaartnummer());
                preparedStatement.setInt(2, product.getProductnummer());
                preparedStatement.execute();
            }
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Product product){
        try {
            PreparedStatement statement1 = this.connection.prepareStatement("" +
                    "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
            statement1.setInt(1, product.getProductnummer());
            statement1.execute();
            statement1.close();
            PreparedStatement statement2 = this.connection.prepareStatement("" +
                    "DELETE FROM product WHERE product_nummer = ?");
            statement2.setInt(1, product.getProductnummer());
            statement2.execute();
            statement2.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        List<Product> producten = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement(
                "SELECT product.product_nummer, naam, beschrijving, prijs FROM product JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer WHERE ov_chipkaart_product.kaart_nummer = ?"
        );
        preparedStatement.setInt(1, ovChipkaart.getKaartnummer());
        ResultSet resultset = preparedStatement.executeQuery();
        while(resultset.next()) {
            Product product = new Product(resultset.getInt("product_nummer"), resultset.getString("naam"), resultset.getString("beschrijving"), resultset.getDouble("prijs"));
            producten.add(product);
        }
        preparedStatement.close();
        resultset.close();
        return producten;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> producten = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement(
                "SELECT product.product_nummer, product.naam, product.beschrijving, product.prijs, ov_chipkaart.kaart_nummer, ov_chipkaart.geldig_tot, ov_chipkaart.klasse, ov_chipkaart.saldo FROM product " +
                        "INNER JOIN ov_chipkaart_product ON product.product_nummer = ov_chipkaart_product.product_nummer " +
                        "INNER JOIN ov_chipkaart ON ov_chipkaart.kaart_nummer = ov_chipkaart_product.kaart_nummer"
        );
        ResultSet resultset = preparedStatement.executeQuery();
        while(resultset.next()) {
            Product product = new Product(resultset.getInt("product_nummer"), resultset.getString("naam"), resultset.getString("beschrijving"), resultset.getDouble("prijs"));
            OVChipkaart ovChipkaart = new OVChipkaart(resultset.getInt("kaart_nummer"), resultset.getDate("geldig_tot"), resultset.getInt("klasse"), resultset.getDouble("saldo"));
            product.addOvchipkaart(ovChipkaart);
            producten.add(product);
            }
        preparedStatement.close();
        resultset.close();
        return producten;
    }
}
