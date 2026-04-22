package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.Rol;

public class Profesor extends Usuario {
    public Profesor(String identificacion, String nombreCompleto, Email email, String password) {
        super(identificacion, nombreCompleto, email, password, Rol.PROFESOR);
    }
}
