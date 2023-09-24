import java.sql.SQLException;

public interface AdresDao {
    public boolean save(Adres adres);
    public boolean update(Adres adres);
    public boolean delete(Adres adres) throws SQLException;
    public Adres findByReiziger(Reiziger reiziger) throws SQLException;
}
