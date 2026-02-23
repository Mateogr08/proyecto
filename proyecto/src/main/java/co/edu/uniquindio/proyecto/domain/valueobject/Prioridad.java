package co.edu.uniquindio.proyecto.domain.valueobject;

public final class Prioridad {
    private final String nivel;
    private final String justificacion;

    public Prioridad(String nivel, String justificacion) {
        if (nivel == null || nivel.isBlank()) throw new IllegalArgumentException("El nivel es obligatorio");
        if (justificacion == null || justificacion.length() < 10) {
            throw new IllegalArgumentException("La justificación de la prioridad debe ser detallada");
        }
        this.nivel = nivel;
        this.justificacion = justificacion;
    }

    public String getNivel() {
        return nivel;
    }
    public String getJustificacion() {
        return justificacion;
    }
}