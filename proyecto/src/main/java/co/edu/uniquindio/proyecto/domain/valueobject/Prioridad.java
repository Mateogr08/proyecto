package co.edu.uniquindio.proyecto.domain.valueobject;

public record Prioridad(String nivel, String justificacion) {
    public Prioridad {
        if (nivel == null || nivel.isBlank()) {
            throw new IllegalArgumentException("El nivel es obligatorio");
        }
        if (justificacion == null || justificacion.length() < 10) {
            throw new IllegalArgumentException("La justificación de la prioridad debe ser detallada (mínimo 10 caracteres)");
        }
    }
}