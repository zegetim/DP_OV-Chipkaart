package domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productnummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<OVChipkaart> ovchipkaarten = new ArrayList<>();

    public Product(int productnummer, String naam, String beschrijving, double prijs) {
        this.productnummer = productnummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public boolean addOvchipkaart(OVChipkaart ovChipkaart){
        if(!ovchipkaarten.contains(ovChipkaart)){
            ovchipkaarten.add(ovChipkaart);
        }
        return false;
    }

    public boolean removeOvchipkaart(OVChipkaart ovChipkaart){
        if (ovchipkaarten.contains(ovChipkaart)){
            ovchipkaarten.remove(ovChipkaart);
        }
        return false;
    }

    public int getProductnummer() {
        return productnummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public List<OVChipkaart> getOvchipkaarten() {
        return ovchipkaarten;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productnummer=" + productnummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", ovchipkaarten=" + ovchipkaarten +
                '}';
    }
}
