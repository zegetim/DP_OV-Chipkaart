package dao;

import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDaoPsql implements ReizigerDao {
    private Connection connection;
    private AdresDaoPsql adresDaoPsql;
    private OVChipkaartDaoPsql ovChipkaartDaoPsql;

    public ReizigerDaoPsql(Connection connection) {
        this.connection = connection;
        this.adresDaoPsql = new AdresDaoPsql(connection);
        this.ovChipkaartDaoPsql = new OVChipkaartDaoPsql(connection);
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        try {
            PreparedStatement statement = this.connection.prepareStatement("" +
                    "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?,?,?,?,?)");
            statement.setInt(1, reiziger.getId());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, reiziger.getGeboortedatum());

            statement.execute();
            statement.close();

            Adres adres = reiziger.getAdres();
            if (adres != null) {
                adres.setReizigerid(reiziger.getId());
                adresDaoPsql.save(adres);
            }

            if(this.ovChipkaartDaoPsql != null){
                for(OVChipkaart ovChipkaart : reiziger.getOvChipkaarten()) {
                    this.ovChipkaartDaoPsql.save(ovChipkaart);
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
    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("" +
                    "UPDATE reiziger SET voorletters = ?,tussenvoegsel = ?,achternaam = ?,geboortedatum = ? WHERE reiziger_id = ?");
            statement.setString(1, reiziger.getVoorletters());
            statement.setString(2, reiziger.getTussenvoegsel());
            statement.setString(3, reiziger.getAchternaam());
            statement.setDate(4, reiziger.getGeboortedatum());
            statement.setInt(5, reiziger.getId());

            statement.execute();
            statement.close();

            Adres adres = reiziger.getAdres();
            if (adres != null) {
                adres.setReizigerid(reiziger.getId());
                adresDaoPsql.update(adres);
            }

            if(this.ovChipkaartDaoPsql != null){
                for(OVChipkaart ovChipkaart : reiziger.getOvChipkaarten()) {
                    this.ovChipkaartDaoPsql.update(ovChipkaart);
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
    public boolean delete(Reiziger reiziger) throws SQLException {
        if(reiziger.getAdres() != null){
            Adres adres = this.adresDaoPsql.findByReiziger(reiziger);
            adresDaoPsql.delete(adres);
        }
        if(this.ovChipkaartDaoPsql != null){
            for(OVChipkaart ovChipkaart : reiziger.getOvChipkaarten()) {
                this.ovChipkaartDaoPsql.delete(ovChipkaart);
            }
        }
        PreparedStatement statement = this.connection.prepareStatement("" +
                        "DELETE FROM reiziger WHERE reiziger_id = ?");
        statement.setInt(1, reiziger.getId());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("" +
                "select reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum from reiziger");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"),resultSet.getString("voorletters"),resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"),resultSet.getDate("geboortedatum"));
            Adres adres = adresDaoPsql.findByReiziger(reiziger);
            reiziger.setAdres(adres);
            List<OVChipkaart> ovchipkaarten = ovChipkaartDaoPsql.findByReiziger(reiziger);
            for(OVChipkaart ovChipkaart : ovchipkaarten){
                reiziger.voegToeOVChipkaart(ovChipkaart);
            }
            reizigers.add(reiziger);
        }
        preparedStatement.close();
        resultSet.close();
        return reizigers;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement("" +
                "select reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum from reiziger where geboortedatum = ?");
        preparedStatement.setDate(1,Date.valueOf(datum));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"),resultSet.getString("voorletters"),resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"),resultSet.getDate("geboortedatum"));
            Adres adres = adresDaoPsql.findByReiziger(reiziger);
            reiziger.setAdres(adres);
            List<OVChipkaart> ovchipkaarten = ovChipkaartDaoPsql.findByReiziger(reiziger);
            for(OVChipkaart ovChipkaart : ovchipkaarten){
                reiziger.voegToeOVChipkaart(ovChipkaart);
            }
            reizigers.add(reiziger);
        }
        preparedStatement.close();
        resultSet.close();
        return reizigers;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Reiziger reiziger = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("" +
                "SELECT reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum FROM reiziger WHERE reiziger_id = ?");
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            reiziger = new Reiziger(resultSet.getInt("reiziger_id"),resultSet.getString("voorletters"),resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"),resultSet.getDate("geboortedatum"));
            Adres adres = adresDaoPsql.findByReiziger(reiziger);
            reiziger.setAdres(adres);
            List<OVChipkaart> ovchipkaarten = ovChipkaartDaoPsql.findByReiziger(reiziger);
            for(OVChipkaart ovChipkaart : ovchipkaarten){
                reiziger.voegToeOVChipkaart(ovChipkaart);
            }
        }
        preparedStatement.close();
        resultSet.close();
        return reiziger;
    }
}
