package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


/**
 * Interface za ustanove
 */

public interface Visokoskolska {



    /**
     * Odredi završnu ocjenu studenta
     * @param listaIspita
     * @param ocjenaIspita
     * @param ocjenaObrane
     * @return
     */
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> listaIspita,int ocjenaIspita, int ocjenaObrane);


    /**
     * Odredi sveukupni prosjek studenata
     * @param ispiti
     * @return
     * @throws NemoguceOdreditiProsjekStudentaException
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal average = BigDecimal.ZERO;
        int count = 0;
        for(Ispit tempIspit : ispiti){
            average = average.add(BigDecimal.valueOf(tempIspit.getOcjena()));
            count ++;
        }
        return average.divide(BigDecimal.valueOf(count));
    }

    /**
     * Vrati ispite po prolaznosti
     * @param ispiti
     * @return
     */
    private List<Ispit> filtrirajPolozeneIspite(List<Ispit> ispiti){
        return  ispiti.stream().filter(ispit -> ispit.getOcjena() > 1).toList();
    }

    /**
     * Filtriraj
     * @param ispiti
     * @param student
     * @return
     */
    default List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student){
        return ispiti.stream().filter(ispit -> ispit.getStudent().getJmbag() == student.getJmbag()).toList();
    }
}
