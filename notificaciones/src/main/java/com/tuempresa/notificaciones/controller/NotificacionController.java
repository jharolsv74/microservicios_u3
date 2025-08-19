package com.tuempresa.notificaciones.controller;

import com.tuempresa.notificaciones.model.Notificacion;
import com.tuempresa.notificaciones.repository.NotificacionRepository;
import com.tuempresa.notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notificaciones")

public class NotificacionController {
    private final NotificacionService service;

    @Autowired
    public NotificacionController(NotificacionService service) {
        this.service = service;
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
