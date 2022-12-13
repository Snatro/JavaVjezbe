package hr.java.vjezbe.entitet;

/**
 * Sadrži gettere i settere profesora
 */
public class Profesor extends Osoba{

    public static class Builder{
        private Long id;
        private String ime;
        private String prezime;
        private String sifra;
        private String titula;

        public Builder(){

        }
        public Builder getId(Long id){
            this.id = id;
            return this;
        }
        public Builder getSifra(String sifra){
            this.sifra = sifra;
            return this;
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
            Profesor profesor = new Profesor(this.ime,this.prezime,this.id);
            profesor.sifra = this.sifra;
            profesor.titula = this.titula;
            return profesor;
        }
    }
    private String sifra;
    private String titula;


    private Profesor(String ime,String prezime,Long id){
        super(ime,prezime,id);
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
