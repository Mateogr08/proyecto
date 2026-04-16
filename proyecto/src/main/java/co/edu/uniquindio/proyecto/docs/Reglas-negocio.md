# Reglas de Negocio

## Solicitud

- El estado de una solicitud "En_atencion" solo puede cambiar a "Atendida" si existe un responsable asignado.

- Ni el solicitante ni la descripción de una solicitud pueden estar vacíos.

- Solo se puede marcar una solicitud como "Atendida" si esta se encuentra en estado "En_atencion" y tiene un responsable asignado.

- Solo se puede cerrar una solicitud si esta se encuentra en estado "Atendida" y tiene una observación registrada.

- Una solicitud se puede cancelar siempre y cuando no haya sido atendida ni cerrada, y el motivo de cancelación no puede estar vacío.

- Solo se puede rechazar una solicitud si esta se encuentra previamente clasificada y se registra el motivo del rechazo.

- A una solicitud se le puede reasignar un responsable siempre y cuando esta se encuentre en estado "En_atencion".

- La prioridad de una solicitud se puede cambiar siempre y cuando esta no se encuentre en estado "Cerrada".


## Usuario

- Un usuario que sea asignado como responsable de una solicitud debe encontrarse en estado activo.


## Reglas conjuntas (Solicitud + Usuario)

- Solo un usuario con rol de administrador puede asignar un responsable a una solicitud; además, la solicitud debe estar clasificada, tener una prioridad definida y el responsable debe estar activo.

- Solo un usuario con rol de administrador puede cerrar una solicitud, siempre y cuando esta se encuentre en estado "Atendida" y tenga una observación registrada.

- Una solicitud puede ser cancelada por el solicitante o por un usuario con rol de administrador, siempre que no haya sido atendida ni cerrada y se registre un motivo de cancelación.

- Solo un usuario con rol de administrador puede reabrir una solicitud si su estado actual es "Cerrada", y es obligatorio registrar un motivo que justifique la reapertura.

- A una solicitud se le puede reasignar un responsable siempre y cuando esta se encuentre en estado "En_atencion" y el nuevo responsable esté activo.