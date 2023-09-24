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
}
