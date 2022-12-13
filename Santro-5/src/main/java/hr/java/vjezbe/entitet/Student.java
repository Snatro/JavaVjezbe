package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Sadrži gettere i settere Studenta
 */
public class Student extends Osoba{

    private String jmbag;
    private LocalDate datumRodenja;

    public Student(String ime, String prezime, String jmbag, LocalDate datumRodenja, Long id) {
        super(ime,prezime,id);
        this.jmbag = jmbag;
        this.datumRodenja = datumRodenja;
    }

    public Student(String ime, String prezime,Long id) {
        super(ime, prezime,id);
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(LocalDate datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getJmbag(), student.getJmbag()) && Objects.equals(getDatumRodenja(), student.getDatumRodenja());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJmbag(), getDatumRodenja());
    }
}
