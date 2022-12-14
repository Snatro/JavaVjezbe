package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Sadrži varijable za objekt Ispit
 */
public final class Ispit extends Entitet implements Online, Serializable {
    private Predmet kolegij;
    private Student student;
    private Integer ocjena;
    private LocalDateTime datumIVrijeme;

    private Dvorana dvorana;


    public Ispit(Long id,Predmet kolegij, Student student, Integer ocjena, LocalDateTime datumIVrijeme, Dvorana dvorana) {
        super(id);
        this.kolegij = kolegij;
        this.student = student;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
        this.dvorana = dvorana;
    }

    public Ispit(Long id) {
        super(id);
    }

    public Predmet getKolegij() {
        return kolegij;
    }

    public void setKolegij(Predmet kolegij) {
        this.kolegij = kolegij;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }

    public Dvorana getDvorana() {
        return dvorana;
    }

    public void setDvorana(Dvorana dvorana) {
        this.dvorana = dvorana;
    }

    @Override
    public void nameOfSoftware(String name) {
        System.out.println("Name of the product used was " + name);
    }
}
