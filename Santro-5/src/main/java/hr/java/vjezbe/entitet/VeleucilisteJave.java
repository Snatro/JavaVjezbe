package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Klasa za Veleučilište Java
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska, Serializable {

    private static final Logger logger
            = LoggerFactory.getLogger(VeleucilisteJave.class);
    public VeleucilisteJave(Long id,String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        super(id,naziv, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        List<ProsjekStudenata> prosjekStudenata = new ArrayList<>();
        for(Student studentNaGodini : this.getStudenti()) {
            List<Ispit> ispitiNaGodini = this.filtrirajIspitePoStudentu(this.getIspiti(),studentNaGodini);
            BigDecimal prosjek = null;
            try {
                for(Ispit provjeriOcjenuIspita : ispitiNaGodini){
                    if(provjeriOcjenuIspita.getOcjena() == 1)
                        throw new NemoguceOdreditiProsjekStudentaException("Ime studenta : " + studentNaGodini.getIme() + " " + studentNaGodini.getPrezime()
                                + " je pao ispit iz " + provjeriOcjenuIspita.getKolegij().getNaziv());
                }
                prosjek = odrediProsjekOcjenaNaIspitima(ispitiNaGodini);
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                System.out.println(e.getMessage());
                prosjek = BigDecimal.valueOf(1);
                logger.error(e.getMessage());
            }
            prosjekStudenata.add(new ProsjekStudenata(studentNaGodini,prosjek));
        }
        ProsjekStudenata najboljiStudent = prosjekStudenata.stream().max((a,b) -> a.getProsjek().compareTo(b.getProsjek())).get();
        return najboljiStudent.getStudent();
    }

    //konačna ocjena = (2 * prosjek ocjena studenta + ocjena završnog rada + ocjena obrane završnog rada) / 4
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> listaIspita, int ocjenaIspita, int ocjenaObrane) {
        double average = listaIspita.stream().mapToDouble(Ispit::getOcjena).average().getAsDouble();
        return BigDecimal.valueOf((2 * average + ocjenaIspita + ocjenaObrane) / 4);
    }

    @Override
    public BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException {
        return Visokoskolska.super.odrediProsjekOcjenaNaIspitima(ispiti);

    }

    @Override
    public List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student) {
        return Visokoskolska.super.filtrirajIspitePoStudentu(ispiti, student);
    }
}
