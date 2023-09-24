import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDao {
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
}
