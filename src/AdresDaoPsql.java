import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdresDaoPsql implements AdresDao{
    private Connection connection;

    public AdresDaoPsql(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean save(Adres adres) {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("INSERT INTO reiziger (adres_id, postcode, huisnummer, straat, woonplaats) " + "VALUES (" + adres.getId() + ", '" +
                    adres.getPostcode() + "', '" +
                    adres.getHuisnummer() + "', '" +
                    adres.getId() + "', '" +
                    adres.getWoonplaats() + "')");
        }
        catch(SQLException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Adres adres) {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("UPDATE reiziger SET postcode = '" + adres.getPostcode() + "', " +
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
        statement.executeQuery("DELETE FROM adres WHERE adres_id = " + adres.getId() + ";");
        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT * FROM adres WHERE reiziger_id = " + reiziger.getId() + ";");
        if(resultset.next()) {
            Adres adres = new Adres(resultset.getInt("adres_id"), resultset.getString("postcode"), resultset.getInt("huisnummer"), resultset.getString("straat"), resultset.getString("woonplaats"));
            return adres;
        }
        return null;
    }
}
