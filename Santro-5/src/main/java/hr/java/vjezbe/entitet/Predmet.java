package hr.java.vjezbe.entitet;


import java.io.Serializable;

/**
 * Klasa za kolegije
 */
public class Predmet extends Entitet implements Serializable {

    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;

    public Predmet(Long id,String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj) {
        super(id);
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;

    }

    public Predmet(Long id) {
        super(id);
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public Profesor getNositelj() {
        return nositelj;
    }

    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }

}
