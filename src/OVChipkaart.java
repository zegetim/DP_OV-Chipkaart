import java.sql.Date;

public class OVChipkaart {
    private int kaartnummer;
    private Date geldig;
    private int klasse;
    private double saldo;
    private Reiziger reiziger;

    public OVChipkaart(int kaartnummer, Date geldig, int klasse, double saldo) {
        this.kaartnummer = kaartnummer;
        this.geldig = geldig;
        this.klasse = klasse;
        this.saldo = saldo;
    }
}
