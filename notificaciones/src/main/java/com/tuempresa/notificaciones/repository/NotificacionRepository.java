package com.tuempresa.notificaciones.repository;

import com.tuempresa.notificaciones.model.Notificacion;
import java.util.*;

public class NotificacionRepository {
    private final Map<Long, Notificacion> notificaciones = new HashMap<>();
    private long idCounter = 1;

    public Notificacion save(Notificacion notificacion) {
        if (notificacion.getId() == null) {
            notificacion.setId(idCounter++);
        }
        notificaciones.put(notificacion.getId(), notificacion);
        return notificacion;
    }

    public List<Notificacion> findAll() {
        return new ArrayList<>(notificaciones.values());
    }
}
