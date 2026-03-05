package co.edu.uniquindio.proyecto.domain.valueobject;

public record TipoSolicitud(String nombre) {
    public TipoSolicitud {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El tipo de solicitud es obligatorio");
        }
    }
}