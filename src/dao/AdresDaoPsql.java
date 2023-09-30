package dao;

import domain.Adres;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) " + "VALUES (" + adres.getId() + ", '" +
                    adres.getPostcode() + "', '" +
                    adres.getHuisnummer() + "', '" +
                    adres.getId() + "', '" +
                    adres.getWoonplaats() + "', '" +
                    adres.getReizigerid() + "')");
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Adres adres) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE reiziger SET postcode = '" + adres.getPostcode() + "', " +
                    "huisnummer = '" + adres.getHuisnummer() + "', " +
                    "straat = '" + adres.getStraat() + "', " +
                    "woonplaats = '" + adres.getWoonplaats() + "' " +
                    "WHERE adres_id = " + adres.getId());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    @Override
    public boolean delete(Adres adres) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM adres WHERE adres_id = " + adres.getId() + ";");
        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id FROM adres WHERE reiziger_id = " + reiziger.getId() + ";");
        if(resultset.next()) {
            Adres adres = new Adres(resultset.getInt("adres_id"), resultset.getString("postcode"), resultset.getInt("huisnummer"), resultset.getString("straat"), resultset.getString("woonplaats"));
            return adres;
        }
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> adresList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("select adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id from adres");
        while (resultset.next()){
            Adres adres = new Adres(resultset.getInt("adres_id"), resultset.getString("postcode"), resultset.getInt("huisnummer"), resultset.getString("straat"), resultset.getString("woonplaats"), resultset.getInt("reiziger_id"));
            adresList.add(adres);
        }
        return adresList;
    }


}
