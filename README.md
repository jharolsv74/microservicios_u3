# 🏥 Microservicios Hospitalarios - U3

Este proyecto implementa una arquitectura de **microservicios dockerizados** con **RabbitMQ** para la comunicación basada en eventos y listeners.  
El sistema está orientado a la **gestión hospitalaria** con enfoque en **turnos, pacientes, médicos y notificaciones**.  

---

## 📌 Objetivo

Diseñar e implementar una arquitectura de microservicios que permita la comunicación asincrónica mediante **RabbitMQ**, con el fin de gestionar de forma eficiente los procesos hospitalarios relacionados con **pacientes, médicos, turnos y notificaciones**.

---

## 📝 Justificación

El uso de microservicios desacoplados y basados en eventos permite:
- Escalabilidad en cada componente de forma independiente.  
- Mejor manejo de la concurrencia en la asignación de turnos.  
- Comunicación **stateless** entre los servicios.  
- Flexibilidad para futuras integraciones.  

---

## 🧩 Microservicios

1. **Notificaciones**  
   - Envía mensajes al paciente y/o médico cuando se genera un turno.  
   - Suscribe eventos desde RabbitMQ.  

2. **Turnos**  
   - Gestión de la reserva y asignación de turnos.  
   - Publica eventos en RabbitMQ.  

3. **Pacientes**  
   - Registro y gestión de pacientes.  
   - Expone endpoints para CRUD de pacientes.  

4. **Médicos**  
   - Administración de médicos.  
   - Relacionado con la asignación de turnos.  

---

## 🖥️ Frontend

El frontend está desarrollado en **React + TypeScript**, el cual consume los endpoints expuestos por los microservicios y muestra la información en una interfaz intuitiva para el usuario.

---

## ⚙️ Tecnologías utilizadas

- **Backend**: Spring Boot (Java)  
- **Mensajería**: RabbitMQ  
- **Frontend**: React + TypeScript  
- **Contenedores**: Docker & Docker Compose  
- **Base de datos**: PostgreSQL / MySQL *(dependiendo de la configuración del proyecto)*  

---

## 🚀 Ejecución del proyecto

### 1. Clonar el repositorio
```bash
git clone https://github.com/<tu-usuario>/microservicios_u3.git
cd microservicios_u3
