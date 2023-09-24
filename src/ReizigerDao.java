import java.sql.SQLException;
import java.util.List;

public interface ReizigerDao {
    public boolean save(Reiziger reiziger) throws SQLException;
    public boolean updateReiziger(Reiziger reiziger) throws SQLException;
    public boolean deleteReiziger(Reiziger reiziger) throws SQLException;
    public List<Reiziger> findAll() throws SQLException;
}
