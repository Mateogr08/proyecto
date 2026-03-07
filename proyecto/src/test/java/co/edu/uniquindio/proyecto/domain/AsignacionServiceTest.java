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

class AsignacionServiceTest {

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