package Dao;

import Dao.ReizigerDao;
import Domain.Adres;
import Domain.OVChipkaart;
import Domain.Reiziger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDaoPsql implements ReizigerDao {
    private Connection connection;

    public ReizigerDaoPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) " + "VALUES (" + reiziger.getId() + ", '" +
                    reiziger.getVoorletters() + "', '" +
                    reiziger.getTussenvoegsel() + "', '" +
                    reiziger.getAchternaam() + "', '" +
                    reiziger.getGeboortedatum() + "')");
            AdresDaoPsql adresDaoPsql = new AdresDaoPsql(connection);
            Adres adres = reiziger.getAdres();
            if (adres != null){
                adres.setReizigerid(reiziger.getId());
                adresDaoPsql.save(adres);
            }
            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE reiziger SET voorletters = '" + reiziger.getVoorletters() + "', " +
                    "tussenvoegsel = '" + reiziger.getTussenvoegsel() + "', " +
                    "achternaam = '" + reiziger.getAchternaam() + "', " +
                    "geboortedatum = '" + reiziger.getGeboortedatum() + "' " +
                    "WHERE reiziger_id = " + reiziger.getId());
            statement.close();
        }
        catch(SQLException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM reiziger WHERE reiziger_id = " + reiziger.getId() + ";");
        if(reiziger.getAdres() != null){
            AdresDaoPsql adresDaoPsql = new AdresDaoPsql(connection);
            Adres adres = adresDaoPsql.findByReiziger(reiziger);
            adresDaoPsql.delete(adres);
        }
        return true;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum from reiziger");
        while (resultSet.next()){
            Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"),resultSet.getString("voorletters"),resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"),resultSet.getDate("geboortedatum"));
            reizigers.add(reiziger);
        }
        return reizigers;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum from reiziger where geboortedatum = '"+ datum +"';");
        while (resultSet.next()){
            Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"),resultSet.getString("voorletters"),resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"),resultSet.getDate("geboortedatum"));
            reizigers.add(reiziger);
        }
        return reizigers;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Reiziger reiziger;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE reiziger_id = " + id + ";");
        if(resultSet.next()) {
            reiziger = new Reiziger(resultSet.getInt("reiziger_id"),resultSet.getString("voorletters"),resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"),resultSet.getDate("geboortedatum"));
            return reiziger;
        }
        return null;
    }
}
