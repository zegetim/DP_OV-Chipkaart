package domain;

import java.sql.Date;
import java.util.ArrayList;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private ArrayList<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
    }

    public boolean voegToeOVChipkaart(OVChipkaart ovChipkaart){
        if(!ovChipkaarten.contains(ovChipkaart)){
            ovChipkaarten.add(ovChipkaart);
        }
        return false;
    }

    public boolean verwijderOVChipkaart(OVChipkaart ovChipkaart){
        if(ovChipkaarten.contains(ovChipkaart)){
            ovChipkaarten.remove(ovChipkaart);
        }
        return false;
    }
    public int getId() {
        return id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public ArrayList<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    @Override
    public String toString() {
        String reiziger = "Reiziger{" +
                "id=" + id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum;
                if(adres!=null) {
                    reiziger += ", adres=" + adres;
                }
                else{
                    reiziger += "geen adres";
                }
                if(ovChipkaarten!=null) {
                    reiziger += ", ovChipkaarten=" + ovChipkaarten ;
                }
                else {
                    reiziger += "geen ovchipkaarten";
                }
                reiziger += '}';
                return reiziger;
    }
}
