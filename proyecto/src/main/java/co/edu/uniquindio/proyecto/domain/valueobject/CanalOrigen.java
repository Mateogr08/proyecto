package co.edu.uniquindio.proyecto.domain.valueobject;

/**
 * Representa los diferentes canales a través de los cuales
 * puede originarse una solicitud en el sistema.
 *
 * <p>Estos canales indican el medio por el cual el usuario
 * realizó la solicitud, lo que permite registrar el origen
 * del trámite y mantener trazabilidad en el proceso.</p>
 *
 * <p>Los valores posibles incluyen canales digitales,
 * telefónicos y presenciales.</p>
 */

public enum CanalOrigen {
    CSU,
    CORREO,
    SAC,
    TELEFONICO,
    PRESENCIAL
}