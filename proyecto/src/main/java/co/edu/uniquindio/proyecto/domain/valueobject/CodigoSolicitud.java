package co.edu.uniquindio.proyecto.domain.valueobject;

public final class CodigoSolicitud {
    private final String valor;

    public CodigoSolicitud(String valor) {
        if (valor == null || !valor.matches("^[A-Z]{3}-\\d{4}$")) {
            throw new IllegalArgumentException("Formato de código inválido (Ej: ABC-1234)");
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
