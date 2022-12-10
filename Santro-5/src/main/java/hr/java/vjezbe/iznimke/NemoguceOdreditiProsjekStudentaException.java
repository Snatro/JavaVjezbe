package hr.java.vjezbe.iznimke;

public class NemoguceOdreditiProsjekStudentaException extends Exception{

    private static final long serialVersionUID = 863316509093583529L;

    public NemoguceOdreditiProsjekStudentaException(){
        super("Student je dobio nedovoljan (1) na ispitu!");
    }

    public NemoguceOdreditiProsjekStudentaException(String message) {
        super(message);
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemoguceOdreditiProsjekStudentaException(Throwable cause) {
        super(cause);
    }
}
