package domain;

import java.sql.Date;

public class OVChipkaart {
    private int kaartnummer;
    private Date geldig;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;

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

    public OVChipkaart(int kaartnummer, Date geldig, int klasse, double saldo) {
        this.kaartnummer = kaartnummer;
        this.geldig = geldig;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartnummer=" + kaartnummer +
                ", geldig=" + geldig +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger +
                '}';
    }
}
