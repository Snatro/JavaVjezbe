package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.LinkLoopException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Main class
 */
public class Glavna {

    private static final Logger logger
            = LoggerFactory.getLogger(Glavna.class);


    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        System.out.println("Unesite broj obrazovnih ustanova: ");
        int brojUstanova = scanner.nextInt();
        scanner.nextLine();

        for(int i = 0; i < brojUstanova; i++) {

            List<Student> listaStudenata = getListaStudenata(scanner);
            List<Profesor> listaProfesora = getListaProfesora(scanner);
            List<Predmet> listaKolegija = getListaKolegija(scanner, listaProfesora);
            kolegijiSViseOd5EctsBodova(listaKolegija);
            List<Ispit> listaIspita = getListaIspita(scanner, listaKolegija, listaStudenata);

            int odabirUstanove = 0;
            boolean flag = false;

            do {
                try {
                    System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti " +
                            "(1 - Veleučilište Jave, 2 - Fakultet računarstva): ");

                    //UBACITI IZNIMKU
                    odabirUstanove = scanner.nextInt();
                    scanner.nextLine();
                    flag = false;
                } catch (Exception e) {
                    logger.error("Krivi upis kod biranja obrazovne ustanove!");
                    flag = true;
                }
            }while(flag);

            System.out.println("Odaberite naziv ustanove : ");
            String nazivUstanove = scanner.nextLine();

            Sveuciliste<ObrazovnaUstanova> sveuciliste = null;

            switch(odabirUstanove) {
                case(1):
                    sveuciliste.dodajObrazovnuUstanovu(new VeleucilisteJave(1L,nazivUstanove, listaKolegija, listaProfesora, listaStudenata, listaIspita));

                break;
                case(2):
                    sveuciliste.dodajObrazovnuUstanovu(new FakultetRacunarstva(1L,nazivUstanove, listaKolegija, listaProfesora, listaStudenata, listaIspita));
                break;
            }

            String nazivRada = sveuciliste.dohvatiObrazovnuUstanovu(i) instanceof VeleucilisteJave ? "Završnog" : "Diplomskog";
            for (Student student : sveuciliste.dohvatiObrazovnuUstanovu(i).getStudenti()){
                System.out.println("Unesite ocjenu "+nazivRada+" rada za studenta: " + student.getIme());
                int ocjenaRada = 1;
                int ocjenaObrane = 1;
                flag = false;
                do {
                    try {
                        //UBACITI IZNIMKU
                        ocjenaRada = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Unesite ocjenu obrane " + nazivRada + " rada za studenta: " + student.getIme());
                        //UBACITI IZNIMKU
                        ocjenaObrane = scanner.nextInt();
                        scanner.nextLine();
                        flag = false;
                    }
                    catch(Exception e){
                        logger.error("Krivi unos kod ocjene studenta! ");
                        flag = true;
                    }
                }while(flag);
                List<Ispit>listaIspitaStudenta;
                BigDecimal ocjenaStudenta;
                if(sveuciliste.dohvatiObrazovnuUstanovu(i) instanceof VeleucilisteJave){
                     listaIspitaStudenta = ((VeleucilisteJave)sveuciliste.dohvatiObrazovnuUstanovu(i)).filtrirajIspitePoStudentu(sveuciliste.dohvatiObrazovnuUstanovu(i).getIspiti(),student);
                     ocjenaStudenta = ((VeleucilisteJave)sveuciliste.dohvatiObrazovnuUstanovu(i)).izracunajKonacnuOcjenuStudijaZaStudenta(listaIspitaStudenta,ocjenaRada,ocjenaObrane);
                }
                else {
                    listaIspitaStudenta = ((FakultetRacunarstva)sveuciliste.dohvatiObrazovnuUstanovu(i)).filtrirajIspitePoStudentu(sveuciliste.dohvatiObrazovnuUstanovu(i).getIspiti(),student);
                    ocjenaStudenta = ((FakultetRacunarstva)sveuciliste.dohvatiObrazovnuUstanovu(i)).izracunajKonacnuOcjenuStudijaZaStudenta(listaIspitaStudenta,ocjenaRada,ocjenaObrane);
                }
                System.out.println("Konačna ocjena studija studenta " + student.getIme() + " " + student.getPrezime()
                + " je " + ocjenaStudenta);
            }
            Student studentGodine = sveuciliste.dohvatiObrazovnuUstanovu(i) instanceof VeleucilisteJave ? ((VeleucilisteJave)sveuciliste.dohvatiObrazovnuUstanovu(i)).odrediNajuspjesnijegStudentaNaGodini(2022) :
                    ((FakultetRacunarstva)sveuciliste.dohvatiObrazovnuUstanovu(i)).odrediNajuspjesnijegStudentaNaGodini(2022);
            System.out.println("Najbolji student 2022 je " + studentGodine.getIme() + " " + studentGodine.getPrezime() + " JMBAG " + studentGodine.getJmbag());
        }

    }

    /**
     * Get list of students
     * @param scan
     * @return
     */
    public static List<Student> getListaStudenata(Scanner scan){
        List<Student> tempList = new ArrayList<>();
        for(int i = 0; i < 2; i++){

            System.out.println("Unesite " + (i+1) + " studenta: ");

            System.out.println("Unesite ime studenta : ");
            String name = scan.nextLine();

            System.out.println("Unesite prezime studenta :");
            String prezime = scan.nextLine();
            System.out.println("Unesite jmbag studenta: ");
            String jmbag = scan.nextLine();

            System.out.println("Unesite datum rođenja: (dd.MM.yyyy) ");
            String datumRodenja = scan.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate datRodenja = LocalDate.parse(datumRodenja,formatter);

            //Student tempStudent = new Student(name,prezime,jmbag,datRodenja);
           // tempList.add(tempStudent);
        }

        return tempList;
    }

    /**
     * Get list of professors
     * @param scan
     * @return
     */
    public static List<Profesor> getListaProfesora(Scanner scan){
        List<Profesor> tempList = new ArrayList<>();

        for(int i = 0; i < 2 ; i++){
            System.out.println("Unesite " + (i+1) + ". profesora :");

            System.out.println("Unesite ime profesora");
            String name = scan.nextLine();

            System.out.println("Unesite prezime profesora");
            String surname = scan.nextLine();

            System.out.println("Unesite titulu profesora");
            String title = scan.nextLine();

            String sifra;
            System.out.println("Unesite sifru profesora");
            sifra = scan.nextLine();

            Profesor tempProfesor = new Profesor.Builder().getSifra(sifra).getIme(name).getPrezime(surname).getTitula(title).build();
            tempList.add(tempProfesor);
        }
        return tempList;
    }

    /**
     * get list of courses
     * @param scan
     * @param listOfProfesor
     * @return
     */
    public static List<Predmet> getListaKolegija(Scanner scan, List<Profesor> listOfProfesor){
        List<Predmet> tempList = new ArrayList<>();
        Map<Profesor,List<Predmet>> mapaProfesora = new HashMap<>();
        for(int i = 0; i < 3; i++){
            System.out.println("unesite informacije " + (i+1) + ". kolegija");

            System.out.println("Unesite naziv kolegija");
            String name = scan.nextLine();

            System.out.println("Unesite šifru kolegija");
            String sifra = scan.nextLine();

            int ects = 0;
            boolean flag = false;
            do {
                try {
                    System.out.println("Unesite broj ects bodova koje nosi taj kolegij");
                    ects = scan.nextInt();
                    scan.nextLine();
                    flag = false;
                }catch(Exception e){
                    logger.error("Krivi upis ects bodova kolegija!");
                    flag = true;
                }
            }while(flag);

            System.out.println("Upišite broj profesora");
            for(int j = 0 ; j < listOfProfesor.size(); j++) {
                System.out.println((j+1) + ". " + listOfProfesor.get(j).getIme() + " " + listOfProfesor.get(j).getPrezime());
            }
            int orderNumber = scan.nextInt() - 1;
            scan.nextLine();
            Predmet tempPredmet = new Predmet(1L,sifra,name,ects,listOfProfesor.get(orderNumber));

            if(mapaProfesora.containsKey(tempPredmet.getNositelj())) {
                List<Predmet> listaKolegija = mapaProfesora.get(tempPredmet.getNositelj());
                listaKolegija.add(tempPredmet);
                mapaProfesora.put(tempPredmet.getNositelj(), listaKolegija);
            } else {
                mapaProfesora.put(tempPredmet.getNositelj(),new ArrayList<>());
            }
            tempList.add(tempPredmet);
        }
        System.out.println(mapaProfesora);
        for(Profesor profesor : mapaProfesora.keySet()){
            System.out.println(profesor.getIme() + " " + profesor.getPrezime() + ": ");
            for(Predmet kolegij: mapaProfesora.get(profesor)){
                System.out.println(kolegij.getNaziv());
            }
        }
        return tempList;
    }

    /**
     * get list of exams
     * @param scan
     * @param listOfPredmet
     * @param listOfStudent
     * @return
     */
    public static List<Ispit> getListaIspita(Scanner scan, List<Predmet> listOfPredmet, List<Student> listOfStudent){
        List<Ispit> tempList = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            System.out.println("Unesite ispitni rok");

            System.out.println("Odaberite kolegij po broju :  ");
            for(int j = 0; j < listOfPredmet.size(); j++){
                System.out.println((j+1) + ". " + listOfPredmet.get(j).getNaziv());
            }
            int kolegijNumber = 0;
            int studentNumber = 0;
            int ocjena = 0;
            boolean flag = false;

            do {
                try {
                    kolegijNumber = scan.nextInt() - 1;
                    scan.nextLine();

                    System.out.println("Odaberite studenta po broju: ");
                    for (int j = 0; j < listOfStudent.size(); j++) {
                        System.out.println((j + 1) + ". " + listOfStudent.get(j).getIme() + " " + listOfStudent.get(j).getPrezime());
                    }
                    studentNumber = scan.nextInt() - 1;
                    scan.nextLine();

                    System.out.println("Unesite ocjenu na ispitu");
                    ocjena = scan.nextInt();
                    scan.nextLine();
                    flag = false;
                }catch(Exception e){
                    logger.error("Pri upisu ispita dogodio se krivi unos!");
                    flag = true;
                }
            }while(flag);
            System.out.println("Unesite datum i vrijeme pisanja ispita u formatu (dd.MM.yyyy.THH:mm)");
            String date = scan.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
            LocalDateTime datumIspita = LocalDateTime.parse(date,formatter);

            System.out.println("Unesite naziv dvorane");
            String nazivDvorane = scan.nextLine();
            System.out.println("Unesite naziv zgrade dvorane");
            String zgrada = scan.nextLine();
            Dvorana dvorana = new Dvorana(nazivDvorane,zgrada);

            Ispit tempIspit = new Ispit(1l,listOfPredmet.get(kolegijNumber),listOfStudent.get(studentNumber),ocjena,datumIspita,dvorana);
            tempList.add(tempIspit);

            if (ocjena == 5)
                System.out.println("Student " + listOfStudent.get(studentNumber).getIme() + " " + listOfStudent.get(studentNumber).getPrezime()
                + " je ostvario ocjenu izvrstan na kolegiju " + listOfPredmet.get(kolegijNumber).getNaziv());
        }
        tempList.stream().filter(student -> student.getOcjena() == 5).forEach(n -> System.out.println("Student " + n.getStudent().getIme() + " " + n.getStudent().getPrezime()
                + " je ostvario ocjenu izvrstan na kolegiju " + n.getKolegij().getNaziv()));

        return tempList;
    }

    public static <T extends Predmet> void kolegijiSViseOd5EctsBodova(List<T> listaKolegija){
        //Prvi zadatak
        List<T> observedKolegiji = listaKolegija.stream().filter(kolegij -> kolegij.getBrojEctsBodova() > 5 ).toList();
        //Drugi zadatak
        Comparator<Predmet> nameComparator = (h1, h2) -> h1.getNaziv().compareTo(h2.getNaziv());
        List<T>unobservedKolegiji = listaKolegija.stream().filter(kolegij -> kolegij.getBrojEctsBodova() <= 5).sorted(nameComparator).toList();
        unobservedKolegiji.stream().forEach(printedKolegij -> System.out.println("MANJE OD 5 " + printedKolegij.getNaziv()));
        // Treći zadatak
        Collections.sort(listaKolegija,new PredmetComparator());
        System.out.println("NIZBRDO");
        listaKolegija.stream().forEach(kolegij -> System.out.println(kolegij.getNaziv()));
        Collections.sort(listaKolegija, new PredmetComparator().reversed());
        System.out.println("UZBRDO");
        listaKolegija.stream().forEach(kolegij -> System.out.println(kolegij.getNaziv()));

    }
}
