
package com.tuempresa.turnos.service;

import com.tuempresa.turnos.model.Turno;
import com.tuempresa.turnos.repository.TurnoRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import com.tuempresa.turnos.model.Notificacion;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TurnoService {
    private final TurnoRepository repository;
    private final RestTemplate restTemplate;


    private final String pacientesServiceUrl;
    private final String medicosServiceUrl;
    private final String notificacionesServiceUrl;

    @Autowired
    public TurnoService(
        TurnoRepository repository,
        @Value("${pacientes.service.url}") String pacientesServiceUrl,
        @Value("${medicos.service.url}") String medicosServiceUrl,
        @Value("${notificaciones.service.url}") String notificacionesServiceUrl
    ) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
        this.pacientesServiceUrl = pacientesServiceUrl;
        this.medicosServiceUrl = medicosServiceUrl;
        this.notificacionesServiceUrl = notificacionesServiceUrl;
    }

    private void notificarTurno(Long pacienteId, String mensaje, String tipo) {
        // Obtener datos del paciente
        String pacienteUrl = pacientesServiceUrl + "/" + pacienteId;
        Object pacienteObj = restTemplate.getForObject(pacienteUrl, Object.class);
        if (pacienteObj == null) {
            System.out.println("[TurnoService] No se pudo obtener datos del paciente para notificación");
            return;
        }
        // Extraer email y telefono del paciente
        String email = null;
        String telefono = null;
        try {
            java.lang.reflect.Method getEmail = pacienteObj.getClass().getMethod("getEmail");
            java.lang.reflect.Method getTelefono = pacienteObj.getClass().getMethod("getTelefono");
            email = (String) getEmail.invoke(pacienteObj);
            telefono = (String) getTelefono.invoke(pacienteObj);
        } catch (Exception e) {
            // Si no se puede usar reflection, intentar como Map
            try {
                java.util.Map map = (java.util.Map) pacienteObj;
                email = (String) map.get("email");
                telefono = (String) map.get("telefono");
            } catch (Exception ex) {
                System.out.println("[TurnoService] Error extrayendo email/telefono del paciente: " + ex.getMessage());
            }
        }
        if (email == null || telefono == null) {
            System.out.println("[TurnoService] No se pudo obtener email o teléfono del paciente para notificación");
            return;
        }
        Notificacion notificacion = new Notificacion();
        notificacion.setPacienteId(pacienteId);
        notificacion.setEmail(email);
        notificacion.setTelefono(telefono);
        notificacion.setMensaje(mensaje);
        notificacion.setFechaHora(LocalDateTime.now());
        notificacion.setTipo(tipo);
        try {
            restTemplate.postForObject(notificacionesServiceUrl, notificacion, Void.class);
            System.out.println("[TurnoService] Notificación enviada: " + mensaje);
        } catch (Exception e) {
            System.out.println("[TurnoService] Error enviando notificación: " + e.getMessage());
        }
    }

    public Turno agendarTurno(Turno turno) {
        // Validar paciente
        String pacienteUrl = pacientesServiceUrl + "/" + turno.getPacienteId();
        System.out.println("[TurnoService] Consultando paciente en: " + pacienteUrl);
        try {
            restTemplate.getForObject(pacienteUrl, Object.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new IllegalArgumentException("El paciente no existe");
            } else {
                throw e;
            }
        }
        // Validar médico
        String medicoUrl = medicosServiceUrl + "/" + turno.getMedicoId();
        System.out.println("[TurnoService] Consultando médico en: " + medicoUrl);
        try {
            restTemplate.getForObject(medicoUrl, Object.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new IllegalArgumentException("El médico no existe");
            } else {
                throw e;
            }
        }
        // Aquí podrías validar disponibilidad, etc.
    turno.setEstado("AGENDADO");
    Turno saved = repository.save(turno);
    String mensaje = "Su turno ha sido agendado para el " + turno.getFechaHora();
    notificarTurno(turno.getPacienteId(), mensaje, "AGENDAMIENTO");
    return saved;
    }

    public Optional<Turno> modificarTurno(Long id, Turno datos) {
        Optional<Turno> turnoOpt = repository.findById(id);
        if (turnoOpt.isPresent()) {
            Turno turno = turnoOpt.get();
            // Validar paciente si cambia
            if (!turno.getPacienteId().equals(datos.getPacienteId())) {
                String pacienteUrl = pacientesServiceUrl + "/" + datos.getPacienteId();
                System.out.println("[TurnoService] Consultando paciente en: " + pacienteUrl);
                try {
                    restTemplate.getForObject(pacienteUrl, Object.class);
                } catch (HttpClientErrorException e) {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new IllegalArgumentException("El paciente no existe");
                    } else {
                        throw e;
                    }
                }
            }
            // Validar médico si cambia
            if (!turno.getMedicoId().equals(datos.getMedicoId())) {
                String medicoUrl = medicosServiceUrl + "/" + datos.getMedicoId();
                System.out.println("[TurnoService] Consultando médico en: " + medicoUrl);
                try {
                    restTemplate.getForObject(medicoUrl, Object.class);
                } catch (HttpClientErrorException e) {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new IllegalArgumentException("El médico no existe");
                    } else {
                        throw e;
                    }
                }
            }
            turno.setFechaHora(datos.getFechaHora());
            turno.setMedicoId(datos.getMedicoId());
            turno.setPacienteId(datos.getPacienteId());
            Turno saved = repository.save(turno);
            String mensaje = "Su turno ha sido modificado para el " + datos.getFechaHora();
            notificarTurno(datos.getPacienteId(), mensaje, "MODIFICACION");
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    public boolean anularTurno(Long id) {
        Optional<Turno> turnoOpt = repository.findById(id);
        if (turnoOpt.isPresent()) {
            Turno turno = turnoOpt.get();
            turno.setEstado("CANCELADO");
            repository.save(turno);
            String mensaje = "Su turno ha sido cancelado.";
            notificarTurno(turno.getPacienteId(), mensaje, "CANCELACION");
            return true;
        }
        return false;
    }

    public List<Turno> listarTurnos() {
        return repository.findAll();
    }

    public Optional<Turno> obtenerTurno(Long id) {
        return repository.findById(id);
    }
}
