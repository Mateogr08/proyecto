package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa el código único que identifica una solicitud dentro del sistema.
 *
 * <p>Este objeto de valor encapsula y valida el formato del identificador
 * de una solicitud. El código debe seguir el patrón definido por tres
 * letras mayúsculas seguidas de un guion y cuatro dígitos numéricos
 * (por ejemplo: ABC-1234).</p>
 *
 * <p>Al tratarse de un Value Object, su valor es inmutable una vez creado.</p>
 */

public final class CodigoSolicitud {

    private final String valor;

    /**
     * Crea un nuevo código de solicitud validando su formato.
     *
     * @param valor cadena que representa el código de la solicitud
     *
     * @throws IllegalArgumentException si el código es nulo
     * o no cumple con el formato esperado (AAA-0000)
     */
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