package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

/**
 * Representa un usuario con rol de administrador dentro del sistema.
 *
 * <p>El administrador es un tipo de {@link Usuario} que posee permisos
 * especiales para gestionar solicitudes dentro del sistema, como
 * realizar su cierre una vez hayan sido atendidas.</p>
 *
 * <p>Esta clase especializa el comportamiento de Usuario indicando
 * que su rol corresponde a <b>ADMINISTRADOR</b>.</p>
 */

public class Administrador extends Usuario {
    public Administrador(String id, String nombre, Email email) {
        super(id, nombre, email);
    }
    @Override
    public String getRol() { return "ADMINISTRADOR"; }
}