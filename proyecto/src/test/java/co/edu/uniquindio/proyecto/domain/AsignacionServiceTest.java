package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.Estudiante;
import co.edu.uniquindio.proyecto.domain.entity.Profesor;
import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.DomainException;
import co.edu.uniquindio.proyecto.domain.service.AsignacionResponsableService;
import co.edu.uniquindio.proyecto.domain.valueobject.CanalOrigen;
import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias para {@link AsignacionResponsableService}.
 *
 * <p>Verifica que las reglas de negocio relacionadas con la asignación
 * de responsables a una solicitud se cumplan correctamente.</p>
 */
class AsignacionServiceTest {

    /**
     * Verifica que no sea posible asignar una solicitud a un
     * usuario que se encuentre inactivo.
     *
     * <p>El sistema debe lanzar una {@link DomainException} cuando
     * se intenta realizar esta operación.</p>
     */
    @Test
    void noDebeAsignarResponsableInactivo() {
        AsignacionResponsableService service = new AsignacionResponsableService();

        Usuario estudiante = new Estudiante("1010", "Pepe", new Email("p@u.co"));
        Solicitud solicitud = new Solicitud(
                new CodigoSolicitud("CAD-1111"),
                TipoSolicitud.CUPO,
                "Solicito cupo en cálculo",
                CanalOrigen.SAC,
                estudiante
        );

        Usuario inactivo = new Profesor("456", "Inactivo", new Email("i@u.co"));
        inactivo.desactivar();

        assertThrows(DomainException.class, () -> {
            service.asignarTramite(solicitud, inactivo);
        });
    }
}