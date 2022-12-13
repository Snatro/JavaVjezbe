package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * Sadrži ime dvorane i ulicu
 * @param naziv
 * @param zgrada
 */
public record Dvorana(String naziv, String zgrada) implements Serializable {
}
