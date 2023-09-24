public class Adres {
    private int id;
    private String postcode;
    private int huisnummer;
    private String straat;
    private String woonplaats;
    private int reizigerid;

    public Adres(int id, String postcode, int huisnummer, String straat, String woonplaats) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
    }

    public int getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public int getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "id=" + id +
                ", postcode='" + postcode + '\'' +
                ", huisnummer=" + huisnummer +
                ", straat='" + straat + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                ", reizigerid=" + reizigerid +
                '}';
    }
}

