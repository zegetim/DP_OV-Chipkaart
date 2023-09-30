package dao;

import domain.Adres;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDao {
    public boolean save(Adres adres);
    public boolean update(Adres adres);
    public boolean delete(Adres adres) throws SQLException;
    public Adres findByReiziger(Reiziger reiziger) throws SQLException;
    public List<Adres> findAll() throws SQLException;
}
