package dao;

import domain.Adres;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDaoPsql implements AdresDao {
    private Connection connection;

    public AdresDaoPsql(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean save(Adres adres) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("" +
                            "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?,?,?,?,?,?)");
            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostcode());
            statement.setInt(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReizigerid());

            statement.execute();
            statement.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("" +
                    "UPDATE adres SET postcode = ?,huisnummer = ?,straat = ?,woonplaats = ?,reiziger_id = ? WHERE adres_id = ?");
            statement.setString(1, adres.getPostcode());
            statement.setInt(2, adres.getHuisnummer());
            statement.setString(3, adres.getStraat());
            statement.setString(4, adres.getWoonplaats());
            statement.setInt(5, adres.getReizigerid());
            statement.setInt(6, adres.getId());

            statement.execute();
            statement.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean delete(Adres adres) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("" +
                "DELETE FROM adres WHERE adres_id = ?");
        statement.setInt(1, adres.getId());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Adres adres = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("" +
                "SELECT adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id FROM adres WHERE reiziger_id = ?");
        preparedStatement.setInt(1,reiziger.getId());
        ResultSet resultset = preparedStatement.executeQuery();
        if(resultset.next()) {
            adres = new Adres(resultset.getInt("adres_id"), resultset.getString("postcode"), resultset.getInt("huisnummer"), resultset.getString("straat"), resultset.getString("woonplaats"), resultset.getInt("reiziger_id"));
        }
        preparedStatement.close();
        resultset.close();
        return adres;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> adresList = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("" +
                        "select adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id from adres");
        ResultSet resultset = preparedStatement.executeQuery();
        while (resultset.next()){
            Adres adres = new Adres(resultset.getInt("adres_id"), resultset.getString("postcode"), resultset.getInt("huisnummer"), resultset.getString("straat"), resultset.getString("woonplaats"), resultset.getInt("reiziger_id"));
            adresList.add(adres);
        }
        preparedStatement.close();
        resultset.close();
        return adresList;
    }
}
