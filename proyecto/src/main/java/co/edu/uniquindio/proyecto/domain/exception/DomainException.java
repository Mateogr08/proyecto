package co.edu.uniquindio.proyecto.domain.exception;

public class DomainException extends RuntimeException {
    public DomainException(String mensaje) {
        super(mensaje);
    }
}
