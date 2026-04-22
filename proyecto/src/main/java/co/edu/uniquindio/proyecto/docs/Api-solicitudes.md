# Especificación de API REST - Gestión de Solicitudes

Este documento describe la interfaz REST para el agregado **Solicitud**, implementada bajo los principios de Arquitectura Hexagonal y diseño orientado al dominio (DDD).

- **Base Path:** `/api/solicitudes`
- **Seguridad:** Requiere cabecera `Authorization: Bearer <token>`.

---

## Endpoints de Gestión

| Método | Endpoint | Acción de Dominio | Descripción |
|:-------|:---------|:------------------|:------------|
| **POST** | `/` | `CrearSolicitud` | Registra una nueva solicitud en el sistema. |
| **GET** | `/all` | `ListarSolicitudes`| Lista solicitudes con soporte para paginación y filtros. |
| **GET** | `/{id}` | `ObtenerSolicitud` | Recupera el detalle completo de una solicitud específica. |
| **GET** | `/{id}/historial` | `VerHistorial` | Obtiene la trazabilidad completa de eventos de la solicitud. |
| **PUT** | `/{id}/clasificar` | `Clasificar` | Define la prioridad y justificación técnica. |
| **PATCH**| `/{id}/responsable`| `Asignar/Reasignar`| Asigna o cambia el usuario responsable de la atención. |
| **POST** | `/{id}/atender` | `Atender` | Marca el inicio de la gestión efectiva (fecha de atención). |
| **PUT** | `/{id}/cerrar` | `Cerrar` | Finaliza el flujo de la solicitud con una observación. |
| **POST** | `/{id}/cancelar` | `Cancelar` | Anulación por parte del solicitante o administrador. |
| **POST** | `/{id}/rechazar` | `Rechazar` | Denegación de la solicitud tras la clasificación. |
| **POST** | `/{id}/reabrir` | `Reabrir` | Reactiva una solicitud previamente cerrada. |

---

##  Modelos de Datos (JSON Examples)

A continuación se presentan los objetos exactos que deben enviarse en el cuerpo (Body) de las peticiones.

### 1. Crear Solicitud (`POST /api/solicitudes`)
```json
{
  "tipo": "CUPO",
  "descripcion": "Solicitud de cupo para la asignatura de Estructuras de Datos, grupo 1.",
  "canal": "WEB",
  "idSolicitante": "est-01"
}
```

### 2. Clasificar Solicitud (`PUT /api/solicitudes/{id}/clasificar`)
*Nota: El {id} de la URL debe coincidir con el idSolicitud del body.*
```json
{
  "idSolicitud": "UUID-DE-LA-SOLICITUD",
  "prioridad": "ALTA",
  "justificacion": "El estudiante presenta cruce de horarios justificado por trabajo.",
  "actorId": "admin-01"
}
```

### 3. Asignar Responsable (`PATCH /api/solicitudes/{id}/responsable`)
```json
{
  "idResponsable": "prof-01"
}
```

### 4. Cerrar Solicitud (`PUT /api/solicitudes/{id}/cerrar`)
```json
{
  "observacion": "Se ha generado el cupo exitosamente en el grupo solicitado."
}
```

### 5. Cancelar Solicitud (`POST /api/solicitudes/{id}/cancelar`)
```json
{
  "motivo": "El estudiante decidió retirar la solicitud por motivos personales."
}
```

### 6. Rechazar Solicitud (`POST /api/solicitudes/{id}/rechazar`)
```json
{
  "motivo": "No se adjuntaron los soportes necesarios para el trámite."
}
```

### 7. Reabrir Solicitud (`POST /api/solicitudes/{id}/reabrir`)
```json
{
  "motivo": "Se requiere revisión adicional ya que el estado no es conforme."
}
```

---

## Decisiones de Diseño

1. **Semántica de Métodos:**
   - Se utiliza **PUT** para acciones que definen un estado final o completo (Clasificar, Cerrar).
   - Se utiliza **PATCH** para actualizaciones parciales que no alteran el flujo principal (Asignación de responsable).
   - Se utiliza **POST** para transiciones de estado que disparan efectos secundarios (Atender, Cancelar).

2. **Encapsulamiento del Agregado:**
   - El historial de eventos no se gestiona como un recurso independiente; se accede a través de la raíz del agregado (`/solicitudes/{id}/historial`).

3. **Paginación:**
   - El endpoint `/all` implementa paginación mediante los parámetros `page` y `size`.
