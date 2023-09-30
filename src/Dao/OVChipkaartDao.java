package Dao;

import Domain.OVChipkaart;
import Domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDao {
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    public boolean save(OVChipkaart ovChipkaart) throws SQLException;
    public boolean update(OVChipkaart ovChipkaart) throws SQLException;
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException;
}
