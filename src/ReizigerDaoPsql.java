import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDaoPsql implements ReizigerDao{
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
            statement.close();
        }
        catch(SQLException e){
            return false;
        }
        return true;
    }
    @Override
    public boolean updateReiziger(Reiziger reiziger) throws SQLException {
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
    public boolean deleteReiziger(Reiziger reiziger) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM reiziger WHERE reiziger_id = " + reiziger.getId() + ";");
        return true;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from reiziger");
        while (resultSet.next()){
            Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"),resultSet.getString("voorletters"),resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"),resultSet.getDate("geboortedatum"));
            reizigers.add(reiziger);
        }
        return reizigers;
    }
}
