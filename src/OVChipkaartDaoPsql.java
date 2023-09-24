import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDaoPsql implements OVChipkaartDao{
    private Connection connection;

    public OVChipkaartDaoPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVChipkaart> ovchipkaarten = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT * FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getId() + ";");
        if(resultset.next()) {
            OVChipkaart ovChipkaart = new OVChipkaart(resultset.getInt("kaart_nummer"), resultset.getDate("geldig_tot"), resultset.getInt("klasse"), resultset.getDouble("saldo"));
            ovchipkaarten.add(ovChipkaart);
        }
        return ovchipkaarten;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) " + "VALUES (" + ovChipkaart.getKaartnummer() + ", '" +
                    ovChipkaart.getGeldig() + "', '" +
                    ovChipkaart.getKlasse() + "', '" +
                    ovChipkaart.getSaldo() + "', '" +
                    ovChipkaart.getReiziger().getId() + "')");
            statement.close();
        }
        catch(SQLException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE ov_chipkaart SET geldig_tot = '" + ovChipkaart.getGeldig() + "', " +
                    "klasse = '" + ovChipkaart.getKlasse() + "', " +
                    "saldo = '" + ovChipkaart.getSaldo() + "', " +
                    "reiziger_id = '" + ovChipkaart.getReiziger().getId() + "' " +
                    "WHERE kaart_nummer = " + ovChipkaart.getKaartnummer());
            statement.close();
        }
        catch(SQLException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM ov_chipkaart WHERE kaart_nummer = " + ovChipkaart.getKaartnummer() + ";");
            statement.close();
        }
        catch(SQLException e){
            return false;
        }
        return true;
    }
}
