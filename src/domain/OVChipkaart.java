package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaartnummer;
    private Date geldig;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;
    private List<Product> producten = new ArrayList<>();

    public OVChipkaart(int kaartnummer, Date geldig, int klasse, double saldo, Reiziger reiziger) {
        this.kaartnummer = kaartnummer;
        this.geldig = geldig;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public OVChipkaart(int kaartnummer, Date geldig, int klasse, double saldo) {
        this.kaartnummer = kaartnummer;
        this.geldig = geldig;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public boolean addProduct(Product product){
        if(!producten.contains(product)){
            producten.add(product);
        }
        return false;
    }

    public boolean removeProduct(Product product){
        if (producten.contains(product)){
            producten.remove(product);
        }
        return false;
    }

    public int getKaartnummer() {
        return kaartnummer;
    }

    public Date getGeldig() {
        return geldig;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartnummer=" + kaartnummer +
                ", geldig=" + geldig +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", producten=" + producten +
                '}';
    }
}
