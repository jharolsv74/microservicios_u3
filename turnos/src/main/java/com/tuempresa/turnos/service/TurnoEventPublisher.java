package com.tuempresa.turnos.service;


import com.tuempresa.turnos.config.RabbitMQConfig;
import com.tuempresa.turnos.model.Turno;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TurnoEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public TurnoEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarCitaConfirmada(Turno turno) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("pacienteId", turno.getPacienteId());
        evento.put("mensaje", "Su cita ha sido " + turno.getEstado().toLowerCase() + " para el " + turno.getFechaHora());
        evento.put("tipo", turno.getEstado());
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.CITA_CONFIRMADA_EXCHANGE,
            RabbitMQConfig.CITA_CONFIRMADA_ROUTING_KEY,
            evento
        );
    }
}
