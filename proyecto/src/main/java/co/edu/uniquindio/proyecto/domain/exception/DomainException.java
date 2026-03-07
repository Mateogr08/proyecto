package co.edu.uniquindio.proyecto.domain.exception;

/**
 * Representa una excepción del dominio del sistema.
 *
 * <p>Esta excepción se utiliza para indicar errores relacionados con
 * las reglas de negocio del dominio. Se lanza cuando ocurre una
 * situación inválida dentro de la lógica del sistema que viola
 * las restricciones o condiciones establecidas.</p>
 *
 * <p>Extiende de {@link RuntimeException} para permitir que las
 * excepciones del dominio se propaguen sin necesidad de ser
 * declaradas explícitamente.</p>
 */

public class DomainException extends RuntimeException {

    /**
     * Crea una nueva excepción de dominio con un mensaje descriptivo.
     * @param mensaje descripción del error ocurrido en el dominio
     */
    public DomainException(String mensaje) {
        super(mensaje);
    }
}