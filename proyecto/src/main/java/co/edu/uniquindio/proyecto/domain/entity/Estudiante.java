package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.Rol;

public class Estudiante extends Usuario {
    public Estudiante(String identificacion, String nombreCompleto, Email email, String password) {
        super(identificacion, nombreCompleto, email, password, Rol.ESTUDIANTE);
    }
}
