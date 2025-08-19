package com.tuempresa.notificaciones.service;

import com.tuempresa.notificaciones.model.Notificacion;
import com.tuempresa.notificaciones.repository.NotificacionRepository;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NotificacionService {
    private final NotificacionRepository repository;

    @Autowired
    public NotificacionService(NotificacionRepository repository) {
        this.repository = repository;
    }

    public Notificacion enviarNotificacion(Notificacion notificacion) {
        notificacion.setFechaHora(LocalDateTime.now());
        // Aquí podrías simular el envío de email/SMS
        return repository.save(notificacion);
    }

    public List<Notificacion> listarNotificaciones() {
        return repository.findAll();
    }
}
