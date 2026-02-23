package co.edu.uniquindio.proyecto.domain.entity;

import co.edu.uniquindio.proyecto.domain.valueobject.Email;

public class Usuario {
    private final String identificacion;
    private final String nombreCompleto;
    private final Email email;
    private boolean activo;

    public Usuario(String identificacion, String nombreCompleto, Email email) {
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.activo = true;
    }

    public boolean isActivo() {
        return activo;
    }
    public String getIdentificacion() {
        return identificacion;
    }

    // Metodo de negocio para cambiar estado sin usar setters directos
    public void desactivar() {
        this.activo = false;
    }
}
