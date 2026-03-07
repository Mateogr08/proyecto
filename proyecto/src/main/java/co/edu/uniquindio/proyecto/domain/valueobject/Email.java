package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa una dirección de correo electrónico dentro del sistema.
 *
 * <p>Este objeto de valor encapsula la dirección de email y valida
 * que tenga un formato básico correcto antes de ser utilizada en
 * el dominio.</p>
 *
 * <p>Al ser un Value Object, su valor es inmutable una vez creado
 * y se utiliza para garantizar la validez del correo electrónico
 * asociado a un usuario.</p>
 */

public final class Email {

    private final String direccion;

    /**
     * Crea una nueva dirección de correo electrónico validando
     * su formato básico.
     *
     * @param direccion dirección de correo electrónico
     *
     * @throws IllegalArgumentException si la dirección es nula
     * o no contiene el símbolo '@'
     */
    public Email(String direccion) {
        if (direccion == null || !direccion.contains("@")) {
            throw new IllegalArgumentException("La dirección de correo electrónico no es válida");
        }
        this.direccion = direccion;
    }

    public String getDireccion() {return direccion;}
}