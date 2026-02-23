package co.edu.uniquindio.proyecto.domain.valueobject;

public final class TipoSolicitud {
    private final String nombre;

    public TipoSolicitud(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El tipo de solicitud es obligatorio");
        }
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}