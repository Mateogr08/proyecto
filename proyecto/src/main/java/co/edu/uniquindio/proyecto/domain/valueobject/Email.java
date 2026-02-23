package co.edu.uniquindio.proyecto.domain.valueobject;

public final class Email {
    private final String direccion;

    public Email(String direccion) {
        if (direccion == null || !direccion.contains("@")) {
            throw new IllegalArgumentException("La dirección de correo electrónico no es válida");
        }
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }
}
