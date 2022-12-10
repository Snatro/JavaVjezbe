package hr.java.vjezbe.entitet;

/**
 * Sadrži gettere i settere profesora
 */
public class Profesor extends Osoba{

    public static class Builder{
        private String ime;
        private String prezime;
        private String sifra;
        private String titula;

        public Builder(String sifra){
            this.sifra = sifra;
        }
        public Builder getIme(String ime){
            this.ime = ime;
            return this;
        }
        public Builder getPrezime(String prezime){
            this.prezime = prezime;
            return this;
        }
        public Builder getTitula(String titula){
            this.titula = titula;
            return this;
        }
        public Profesor build(){
            Profesor profesor = new Profesor(this.ime,this.prezime);
            profesor.sifra = this.sifra;
            profesor.titula = this.titula;
            return profesor;
        }
    }
    private String sifra;
    private String titula;


    private Profesor(String ime,String prezime){
        super(ime,prezime);
    }


    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }
}
