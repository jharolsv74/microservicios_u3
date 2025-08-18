package com.tuempresa.notificaciones.controller;

import com.tuempresa.notificaciones.model.Notificacion;
import com.tuempresa.notificaciones.repository.NotificacionRepository;
import com.tuempresa.notificaciones.service.NotificacionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    private final NotificacionService service;

    public NotificacionController() {
        this.service = new NotificacionService(new NotificacionRepository());
    }

    @PostMapping
    public Notificacion enviarNotificacion(@RequestBody Notificacion notificacion) {
        return service.enviarNotificacion(notificacion);
    }

    @GetMapping
    public List<Notificacion> listarNotificaciones() {
        return service.listarNotificaciones();
    }
}
