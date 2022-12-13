package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class GlavnaDatoteke {

    public static final String FILE_NAME_PROFESORI = "dat/Profesori";
    public static final String FILE_NAME_STUDENTI = "dat/Studenti";
    public static final String FILE_NAME_PREDMETI = "dat/Predmeti";
    public static final String FILE_NAME_ISPITI = "dat/Ispiti";
    public static final String FILE_NAME_OBRAZOVNE_USTANOVE = "dat/obrazovne-ustanove";
    public static final Integer PROFESORI_LINE_COUNT = 5;
    public static final Integer STUDENTI_LINE_COUNT = 5;
    public static final Integer PREDMETI_LINE_COUNT = 5;
    public static final Integer ISPITI_LINE_COUNT = 7;
    public static final Integer OBRAZOVNE_USTANOVE_COUNT = 6;



    public static void main(String[] args) {
        List<Profesor> profesors = dohvatiProfesore();
        List<Student> students = dohvatiStudente();
        List<Predmet> predmeti = dohvatiKolegije(profesors);
        List<Ispit> ispiti = dohvatiIspite(predmeti,students);
        List<ObrazovnaUstanova> obrazovneUstanove = dohvatiUstanove(profesors,predmeti,students,ispiti);
        ispisPodataka(obrazovneUstanove);

    }

    public static void ispisPodataka(List<ObrazovnaUstanova> obrazovneUstanove)  {
        for(ObrazovnaUstanova obrazovnaUstanova : obrazovneUstanove){
            listaProfesoraIKolegija(obrazovnaUstanova.getProfesori(),obrazovnaUstanova.getPredmeti());
        }
        for (ObrazovnaUstanova obrazovnaUstanova : obrazovneUstanove){
            obrazovnaUstanova.getIspiti().stream().filter(x -> x.getOcjena() == 5).forEach(y -> System.out.println(y.getStudent().getIme() + " "
                    + y.getStudent().getPrezime() + " je dobio ocjenu izvrstan iz " + y.getKolegij().getNaziv()));
        }
        for(ObrazovnaUstanova obrazovnaUstanova : obrazovneUstanove){
            for(Student student : obrazovnaUstanova.getStudenti()) {
                BigDecimal ocjena = BigDecimal.ZERO;
                List<Ispit> listaIspitaPoStudentu;


                System.out.println("Konačna ocjena studija studenta " + student.getIme() + " " + student.getPrezime()
                        + " je " + ocjena);
            }

        }
        System.out.println("serijalizacija..");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dat/obrazovne-ustanove.dat"));
            for (ObrazovnaUstanova obrazovnaUstanova : obrazovneUstanove) {
                out.writeObject(obrazovnaUstanova);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void listaProfesoraIKolegija(List<Profesor> profesori, List<Predmet> kolegij){
        Map<Profesor,List<Predmet>> mapaProfesora = new HashMap<>();
        for(Predmet predmet : kolegij){
            for(Profesor profesor : profesori){
                if(predmet.getNositelj().equals(profesor)){
                    mapaProfesora.computeIfAbsent(profesor, k -> new ArrayList<>()).add(predmet);
                }else{
                    mapaProfesora.put(profesor, new ArrayList<>());
                }
            }
        }
        for (Profesor profesor : mapaProfesora.keySet()){
            if (mapaProfesora.get(profesor).isEmpty())
                System.out.println("Profesor " + profesor.getIme() + " " + profesor.getPrezime() + " nema kolegija!");
            else {
                System.out.println("Profesor " + profesor.getIme() + " " + profesor.getPrezime() + " predaje sljedeće kolegije: ");
                mapaProfesora.get(profesor).stream().forEach(x -> System.out.println(x.getNaziv()));

            }
        }

    }

    public static List<Profesor> dohvatiProfesore(){
        List<Profesor> profesors = new ArrayList<>();
        File profesorFile = new File(FILE_NAME_PROFESORI);

        try(BufferedReader profesorFileReader = new BufferedReader(new FileReader(profesorFile))){
            String line;
            Integer counter = 1;

            Profesor.Builder profesor = new Profesor.Builder();
            System.out.println("Učitavanje profesora...");
            while((line = profesorFileReader.readLine()) != null){
                if(counter % PROFESORI_LINE_COUNT == 1)
                    profesor.getId(Long.parseLong(line));
                else if(counter % PROFESORI_LINE_COUNT == 2)
                    profesor.getSifra(line);
                else if(counter % PROFESORI_LINE_COUNT == 3)
                    profesor.getIme(line);
                else if(counter % PROFESORI_LINE_COUNT == 4)
                    profesor.getPrezime(line);
                else if(counter % PROFESORI_LINE_COUNT == 0){
                    profesor.getTitula(line);
                    Profesor newProfesor = profesor.build();
                    profesors.add(newProfesor);
                    profesor = new Profesor.Builder();
                }
                counter++;

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            return profesors;
    }
    public static List<Student> dohvatiStudente(){
        List<Student> students = new ArrayList<>();
        File studentFile = new File(FILE_NAME_STUDENTI);

        try(BufferedReader studentFileReader = new BufferedReader(new FileReader(studentFile))){
            String line;
            Integer counter = 1;
            System.out.println("Učitavanje studenata...");
            Student student = new Student(null,null,null);
            while((line = studentFileReader.readLine()) != null){
                if(counter % STUDENTI_LINE_COUNT == 1)
                    student.setId(Long.parseLong(line));
                else if(counter % STUDENTI_LINE_COUNT == 2)
                    student.setJmbag(line);
                else if(counter % STUDENTI_LINE_COUNT == 3)
                    student.setIme(line);
                else if(counter % STUDENTI_LINE_COUNT == 4)
                    student.setPrezime(line);
                else if(counter % STUDENTI_LINE_COUNT == 0){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate datRodenja = LocalDate.parse(line,formatter);
                    student.setDatumRodenja(datRodenja);
                    students.add(student);
                    student = new Student(null,null,null);
                }
                counter++;

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return students;
    }
    public static List<Predmet> dohvatiKolegije(List<Profesor> profesors){
        List<Predmet> predmeti = new ArrayList<>();
        File predmetFile = new File(FILE_NAME_PREDMETI);

        try(BufferedReader predmetFileReader = new BufferedReader(new FileReader(predmetFile))){
            String line;
            Integer counter = 1;
            Predmet predmet = new Predmet(null);
            System.out.println("Učitavanje predmeta...");
            while((line = predmetFileReader.readLine()) != null){
                if(counter % PREDMETI_LINE_COUNT == 1) {
                    predmet.setId(Long.parseLong(line));

                }
                else if(counter % PREDMETI_LINE_COUNT == 2) {
                    predmet.setSifra(line);
                }
                else if(counter % PREDMETI_LINE_COUNT == 3) {
                    predmet.setNaziv(line);
                }
                else if(counter % PREDMETI_LINE_COUNT == 4) {
                    predmet.setBrojEctsBodova(Integer.parseInt(line));
                }
                else if(counter % PREDMETI_LINE_COUNT == 0){
                    predmet.setNositelj(profesors.get(Integer.parseInt(line) - 1));
                    predmeti.add(predmet);
                    predmet = new Predmet(null);
                }
                counter++;

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return predmeti;
    }
    public static List<Ispit> dohvatiIspite(List<Predmet> predmeti, List<Student> studenti){
        List<Ispit> ispiti = new ArrayList<>();
        File ispitFile = new File(FILE_NAME_ISPITI);

        try(BufferedReader ispitFileReader = new BufferedReader(new FileReader(ispitFile))){
            String line;
            Integer counter = 1;
            System.out.println("Učitavanje ispita...");
           Ispit ispit = new Ispit(null);
           String nazivDvorane = "";
            while((line = ispitFileReader.readLine()) != null){
                if(counter % ISPITI_LINE_COUNT == 1)
                    ispit.setId(Long.parseLong(line));
                else if(counter % ISPITI_LINE_COUNT == 2)
                    ispit.setKolegij(predmeti.get(Integer.parseInt(line)));
                else if(counter % ISPITI_LINE_COUNT == 3)
                    ispit.setStudent(studenti.get(Integer.parseInt(line)));
                else if(counter % ISPITI_LINE_COUNT == 4)
                    ispit.setOcjena(Integer.parseInt(line));
                else if(counter % ISPITI_LINE_COUNT == 5){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm");
                    String date = line;
                    LocalDateTime datumIspita = LocalDateTime.parse(date,formatter);
                    ispit.setDatumIVrijeme(datumIspita);

                }
                else if(counter % ISPITI_LINE_COUNT == 6)
                    nazivDvorane = line;
                else if(counter % ISPITI_LINE_COUNT == 0){
                    ispit.setDvorana(new Dvorana(nazivDvorane,line));
                    nazivDvorane = "";
                    ispiti.add(ispit);
                    ispit = new Ispit(null);
                }
                counter++;

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ispiti;
    }

    public static List<ObrazovnaUstanova> dohvatiUstanove(List<Profesor> profesori, List<Predmet> predmeti, List<Student> studenti, List<Ispit> ispiti){
        List<ObrazovnaUstanova> obrazovneUstanove = new ArrayList<>();
        try(Scanner in = new Scanner(new FileReader(FILE_NAME_OBRAZOVNE_USTANOVE))) {
            System.out.println("Učitavanje obrazovnih ustanova...");
            while (in.hasNextLine()) {
                Long id = in.nextLong();
                in.nextLine();
                String nazivUstanova = in.nextLine();
                String predmetiString = in.nextLine().replaceAll("\\s+", "");
                List<Predmet> predmetiUstanove = new ArrayList<>();
                for (Character character : predmetiString.toCharArray()) {
                    predmetiUstanove.add(
                            predmeti.stream()
                                    .filter(pred -> pred.getId().equals(Long.parseLong(String.valueOf(character))))
                                    .findFirst().orElseThrow()
                    );
                }
                String studentiUstanoveString = in.nextLine().replaceAll("\\s+", "");
                List<Student> studentiUstanove = new ArrayList<>();
                for(Character character : studentiUstanoveString.toCharArray()){
                    studentiUstanove.add(
                            studenti.stream()
                                    .filter(st -> st.getId().equals(Long.parseLong(String.valueOf(character))))
                                    .findFirst().orElseThrow()
                    );
                }

                String profesoriUstanoveString = in.nextLine().replaceAll("\\s+", "");
                List<Profesor> profesoriUstanove = new ArrayList<>();
                for(Character character : profesoriUstanoveString.toCharArray()){
                    profesoriUstanove.add(
                            profesori.stream()
                                    .filter(prof -> prof.getId().equals(Long.parseLong(String.valueOf(character))))
                                    .findFirst().orElseThrow()
                    );
                }

                String ispitiUstanoveString = in.nextLine().replaceAll("\\s+", "");
                List<Ispit> ispitiUstanove = new ArrayList<>();
                for(Character character : ispitiUstanoveString.toCharArray()){
                    ispitiUstanove.add(
                            ispiti.stream()
                                    .filter(isp -> isp.getId().equals(Long.parseLong(String.valueOf(character))))
                                    .findFirst().orElseThrow()
                    );
                }

                if(id.equals(1L)){
                    VeleucilisteJave veleucilisteJave = new VeleucilisteJave(id, nazivUstanova, predmetiUstanove,
                            profesoriUstanove,studentiUstanove,  ispitiUstanove);
                    obrazovneUstanove.add(veleucilisteJave);
                } else if (id.equals(2L)) {
                    FakultetRacunarstva fakultetRacunarstva = new FakultetRacunarstva(id, nazivUstanova, predmetiUstanove,
                            profesoriUstanove, studentiUstanove, ispitiUstanove);
                    obrazovneUstanove.add(fakultetRacunarstva);
                }
            }
        }
        catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        return obrazovneUstanove;
    }
}
