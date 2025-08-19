package com.tuempresa.notificaciones.service;

import com.tuempresa.notificaciones.model.Notificacion;
import com.tuempresa.notificaciones.repository.NotificacionRepository;
import com.tuempresa.notificaciones.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TurnoEventListener {
    private final NotificacionRepository repository;
    private final RestTemplate restTemplate;
    private final String pacientesServiceUrl;

    public TurnoEventListener(NotificacionRepository repository,
                              @Value("${pacientes.service.url}") String pacientesServiceUrl) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
        this.pacientesServiceUrl = pacientesServiceUrl;
    }

    @RabbitListener(queues = RabbitMQConfig.CITA_CONFIRMADA_QUEUE)
    public void recibirCitaConfirmada(Map<String, Object> turno) {
        Long pacienteId = ((Number) turno.get("pacienteId")).longValue();
        String mensaje = (String) turno.get("mensaje");
        String tipo = (String) turno.get("tipo");

        // Buscar datos del paciente
        String pacienteUrl = pacientesServiceUrl + "/" + pacienteId;
        String email = null;
        String telefono = null;
        try {
            Map paciente = restTemplate.getForObject(pacienteUrl, Map.class);
            if (paciente != null) {
                email = (String) paciente.get("email");
                telefono = (String) paciente.get("telefono");
            }
        } catch (Exception e) {
            System.err.println("[TurnoEventListener] Error consultando paciente: " + e.getMessage());
            return;
        }
        if (email == null || telefono == null) {
            System.err.println("[TurnoEventListener] Email o teléfono no encontrado para paciente " + pacienteId);
            return;
        }
        Notificacion notificacion = new Notificacion();
        notificacion.setPacienteId(pacienteId);
        notificacion.setEmail(email);
        notificacion.setTelefono(telefono);
        notificacion.setMensaje(mensaje);
        notificacion.setFechaHora(LocalDateTime.now());
        notificacion.setTipo(tipo);
        repository.save(notificacion);
        System.out.println("[TurnoEventListener] Notificación generada para paciente " + pacienteId);
    }
}
