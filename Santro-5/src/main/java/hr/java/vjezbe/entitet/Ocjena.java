package hr.java.vjezbe.entitet;

public enum Ocjena {

    NEDOVOLJAN(1,"Nedovoljan"),
    DOVOLJAN(2,"Dovoljan"),
    DOBAR(3,"Dobar"),
    VRLO_DOBAR(4,"Vrlo dobar"),
    IZVRSTAN(5,"Izvrstan");
    private int ocjena;
    private String naziv;

    Ocjena(int ocjena, String naziv) {
        this.ocjena = ocjena;
        this.naziv = naziv;
    }
}
