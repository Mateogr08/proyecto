package co.edu.uniquindio.proyecto.domain.valueobject;

public enum TipoSolicitud {
    REGISTRO_ASIGNATURAS("Registro de asignaturas", "ALTA"),
    HOMOLOGACION("Homologación", "BAJA"),
    CANCELACION("Cancelación de asignaturas", "MEDIA"),
    CUPO("Solicitud de cupos", "ALTA"),
    CONSULTA("Consulta académica", "BAJA");

    private final String nombre;
    private final String prioridadSugerida;

    TipoSolicitud(String nombre, String prioridadSugerida) {
        this.nombre = nombre;
        this.prioridadSugerida = prioridadSugerida;
    }

    public String getNombre() { return nombre; }
    public String getPrioridadSugerida() { return prioridadSugerida; }
}