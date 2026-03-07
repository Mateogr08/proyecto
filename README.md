Sistema de Triage y Gestión de Solicitudes Académicas
Este proyecto consiste en el desarrollo de una plataforma robusta para la gestión eficiente de solicitudes 
académicas y administrativas del Programa de Ingeniería de Sistemas y Computación.

Integrantes:
- Mateo Garrido Rios
- Camila Ramirez Galvis

Descripción del Proyecto:
El sistema aborda la problemática de ineficiencia y falta de trazabilidad en la gestión de solicitudes para una 
comunidad de más de 1.400 usuarios. Centraliza trámites como registros de asignaturas, homologaciones y cupos, eliminando 
la dispersión causada por el uso de múltiples canales (correo, presencial, etc.).
Características Principales:
- Clasificación y Priorización: Motor de reglas para organizar solicitudes según impacto académico y fecha límite.
- Ciclo de Vida Controlado: Gestión de estados desde el registro hasta el cierre definitivo, garantizando transiciones coherentes.
- Trazabilidad Total: Historial auditable que registra cada acción, responsable y observación.
- Asistencia por IA: Integración opcional de modelos de lenguaje para sugerir tipos de solicitud y generar resúmenes.

Guía de Compilación y Pruebas:
Para ejecutar el proyecto es necesario contar con:
- Java JDK 25
- Gradle
- Un entorno de desarrollo

Compilar el proyecto:
Para compilar el código fuente del proyecto ejecutar: gradle build
Para ejecutar todas las pruebas del sistema: gradle test

Hitos del Proyecto:
- Hito 1 (Semanas 1-5): Diseño, modelado de dominio UML y codificación.
- Hito 2 (Semanas 6-10): Lógica de negocio, persistencia y motor de reglas.
- Hito 3 (Semanas 11-15): Interfaz de usuario, seguridad JWT y despliegue.

Stack Tecnológico:
- Backend: Java (Spring Boot)
- Frontend: TypeScript (Angular)
- Arquitectura: API REST con persistencia ORM.
