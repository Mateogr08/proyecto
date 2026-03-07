package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

public class Profesor extends Usuario {
    public Profesor(String id, String nombre, Email email) {
        super(id, nombre, email);
    }
    @Override
    public String getRol() { return "PROFESOR"; }
}