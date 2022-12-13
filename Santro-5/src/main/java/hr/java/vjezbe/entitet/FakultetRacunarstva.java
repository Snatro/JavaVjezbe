package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Klasa za Fakultet Računarstva
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{

    private static final Logger logger
            = LoggerFactory.getLogger(FakultetRacunarstva.class);
    public FakultetRacunarstva(Long id,String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti) {
        super(id,naziv, predmeti, profesori, studenti, ispiti);
    }

    @Override
    public Student odrediStudentaZaRektorovuNagradu() {
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

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {

        //UDARI COUNTER
       List<Student> observedStudents = new ArrayList<>();
        int []counter = new int[this.getStudenti().size()];
        int count = 0;
        for(Student student : observedStudents){
            List<Ispit> ispiti = filtrirajIspitePoStudentu(this.getIspiti(),student);
            for(Ispit observedIspit : ispiti){
                if(observedIspit.getOcjena() == 5)
                    counter[count] += 1;
            }
            count++;
        }
        int maxAt = 0;
        for (int i = 0; i < counter.length; i++) {
            maxAt = counter[i] > counter[maxAt] ? i : maxAt;
        }
        return observedStudents.get(maxAt);
    }
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> listaIspita, int ocjenaIspita, int ocjenaObrane) {
        double average = listaIspita.stream().mapToDouble(Ispit::getOcjena).average().getAsDouble();
        return BigDecimal.valueOf((3 * average + ocjenaIspita + ocjenaObrane) / 5);
    }

    @Override
    public BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException{
        return Diplomski.super.odrediProsjekOcjenaNaIspitima(ispiti);
    }

    @Override
    public List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student) {
        return Diplomski.super.filtrirajIspitePoStudentu(ispiti, student);
    }
}
