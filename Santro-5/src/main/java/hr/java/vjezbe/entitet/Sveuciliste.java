package hr.java.vjezbe.entitet;

import java.util.List;

public class Sveuciliste<T> extends ObrazovnaUstanova{

    private List<T> obrazovneUstanove;

    public Sveuciliste(String naziv, List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti, List<Ispit> ispiti, List<T> obrazovneUstanove) {
        super(naziv, predmeti, profesori, studenti, ispiti);
        this.obrazovneUstanove = obrazovneUstanove;
    }

    public void dodajObrazovnuUstanovu(T obrazovnaUstanova){
        this.obrazovneUstanove.add(obrazovnaUstanova);
    }
    public T dohvatiObrazovnuUstanovu(int index){
        return this.obrazovneUstanove.get(index);
    }

    private List<T> getObrazovneUstanove(){
        return this.obrazovneUstanove;
    }
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        return null;
    }
}
