package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

/**
 * Representa un usuario con rol de estudiante dentro del sistema.
 *
 * <p>El estudiante es un tipo de {@link Usuario} que puede crear y
 * gestionar solicitudes académicas a través del sistema. Hereda
 * los atributos y comportamientos básicos definidos en la clase
 * Usuario.</p>
 *
 * <p>Esta clase especializa el comportamiento de Usuario indicando
 * que su rol corresponde a <b>ESTUDIANTE</b>.</p>
 */

public class Estudiante extends Usuario {
    public Estudiante(String id, String nombre, Email email) {
        super(id, nombre, email);
    }
    @Override
    public String getRol() { return "ESTUDIANTE"; }
}
