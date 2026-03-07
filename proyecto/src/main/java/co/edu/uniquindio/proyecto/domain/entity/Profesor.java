package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

/**
 * Representa un usuario con rol de profesor dentro del sistema.
 *
 * <p>El profesor es un tipo de {@link Usuario} que puede participar
 * en la gestión o atención de solicitudes académicas dentro del sistema.
 * Hereda los atributos y comportamientos básicos definidos en la clase Usuario.</p>
 *
 * <p>Esta clase especializa el comportamiento de Usuario indicando
 * que su rol corresponde a <b>PROFESOR</b>.</p>
 */

public class Profesor extends Usuario {
    public Profesor(String id, String nombre, Email email) {
        super(id, nombre, email);
    }
    @Override
    public String getRol() { return "PROFESOR"; }
}