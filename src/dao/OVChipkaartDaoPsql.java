package dao;

import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDaoPsql implements OVChipkaartDao {
    private Connection connection;
    private ProductDaoPsql productDao;

    public OVChipkaartDaoPsql(Connection connection) {
        this.connection = connection;
        this.productDao = new ProductDaoPsql(connection);
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?,?,?,?,?)");
            preparedStatement.setInt(1,ovChipkaart.getKaartnummer());
            preparedStatement.setDate(2,ovChipkaart.getGeldig());
            preparedStatement.setInt(3,ovChipkaart.getKlasse());
            preparedStatement.setDouble(4,ovChipkaart.getSaldo());
            preparedStatement.setInt(5,ovChipkaart.getReiziger().getId());

            preparedStatement.execute();
            preparedStatement.close();
            if (this.productDao != null) {
                for (Product product: ovChipkaart.getProducten()) {
                    this.productDao.save(product);
                }
            }
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE ov_chipkaart SET geldig_tot = ?,klasse = ?,saldo = ?,reiziger_id = ? WHERE kaart_nummer = ?");
            preparedStatement.setDate(1,ovChipkaart.getGeldig());
            preparedStatement.setInt(2,ovChipkaart.getKlasse());
            preparedStatement.setDouble(3,ovChipkaart.getSaldo());
            preparedStatement.setInt(4,ovChipkaart.getReiziger().getId());
            preparedStatement.setInt(5,ovChipkaart.getKaartnummer());

            preparedStatement.execute();
            preparedStatement.close();
            if (this.productDao != null) {
                for (Product product: ovChipkaart.getProducten()) {
                    this.productDao.update(product);
                }
            }
            return true;
       }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        try{
            PreparedStatement statement1 = this.connection.prepareStatement("" +
                    "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?");
            statement1.setInt(1, ovChipkaart.getKaartnummer());
            statement1.execute();
            statement1.close();
            PreparedStatement statement2 = this.connection.prepareStatement("" +
                    "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
            statement2.setInt(1, ovChipkaart.getKaartnummer());
            statement2.execute();
            statement2.close();

        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVChipkaart> ovchipkaarten = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("" +
                "SELECT kaart_nummer, geldig_tot, klasse, saldo FROM ov_chipkaart WHERE reiziger_id = ?");
        preparedStatement.setInt(1,reiziger.getId());
        ResultSet resultset = preparedStatement.executeQuery();
        while(resultset.next()) {
            OVChipkaart ovChipkaart = new OVChipkaart(resultset.getInt("kaart_nummer"), resultset.getDate("geldig_tot"), resultset.getInt("klasse"), resultset.getDouble("saldo"));
            List<Product> producten = productDao.findByOVChipkaart(ovChipkaart);
            ovChipkaart.setProducten(producten);
//            ovChipkaart.setReiziger(reiziger);
            ovchipkaarten.add(ovChipkaart);
        }
        preparedStatement.close();
        resultset.close();
        return ovchipkaarten;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("" +
                "SELECT kaart_nummer, geldig_tot, klasse, saldo FROM ov_chipkaart");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            OVChipkaart ovChipkaart = new OVChipkaart(resultSet.getInt("kaart_nummer"),resultSet.getDate("geldig_tot"),resultSet.getInt("klasse"), resultSet.getDouble("saldo"));
            List<Product> producten = productDao.findByOVChipkaart(ovChipkaart);
            ovChipkaart.setProducten(producten);
            ovChipkaarten.add(ovChipkaart);
        }
        preparedStatement.close();
        resultSet.close();
        return ovChipkaarten;
    }
}
