package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

public class Estudiante extends Usuario {
    public Estudiante(String id, String nombre, Email email) {
        super(id, nombre, email);
    }
    @Override
    public String getRol() { return "ESTUDIANTE"; }
}
