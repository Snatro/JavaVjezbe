package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class ProsjekStudenata {

   private Student student;
   private BigDecimal prosjek;

    public ProsjekStudenata(Student student, BigDecimal prosjek) {
        this.student = student;
        this.prosjek = prosjek;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public BigDecimal getProsjek() {
        return prosjek;
    }

    public void setProsjek(BigDecimal prosjek) {
        this.prosjek = prosjek;
    }
}
