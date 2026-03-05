package co.edu.uniquindio.proyecto.domain;

import co.edu.uniquindio.proyecto.domain.entity.Solicitud;
import co.edu.uniquindio.proyecto.domain.entity.Usuario;
import co.edu.uniquindio.proyecto.domain.exception.DomainException;
import co.edu.uniquindio.proyecto.domain.service.AsignacionResponsableService;
import co.edu.uniquindio.proyecto.domain.valueobject.CodigoSolicitud;
import co.edu.uniquindio.proyecto.domain.valueobject.Email;
import co.edu.uniquindio.proyecto.domain.valueobject.TipoSolicitud;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AsignacionServiceTest {

    @Test
    void noDebeAsignarResponsableInactivo() {
        AsignacionResponsableService service = new AsignacionResponsableService();
        Solicitud solicitud = new Solicitud(new CodigoSolicitud("CAD-1111"), new TipoSolicitud("Cupo"));
        Usuario inactivo = new Usuario("456", "Inactivo", new Email("i@u.co"));
        inactivo.desactivar();

        assertThrows(DomainException.class, () -> {
            service.asignarTramite(solicitud, inactivo);
        });
    }
}