
package com.tuempresa.turnos.service;

import com.tuempresa.turnos.model.Turno;
import com.tuempresa.turnos.repository.TurnoRepository;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.tuempresa.turnos.service.TurnoEventPublisher;
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
    private final TurnoEventPublisher eventPublisher;

    @Autowired
    public TurnoService(
        TurnoRepository repository,
        @Value("${pacientes.service.url}") String pacientesServiceUrl,
        @Value("${medicos.service.url}") String medicosServiceUrl,
        TurnoEventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
        this.pacientesServiceUrl = pacientesServiceUrl;
        this.medicosServiceUrl = medicosServiceUrl;
        this.eventPublisher = eventPublisher;
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
        eventPublisher.publicarCitaConfirmada(saved);
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
            eventPublisher.publicarCitaConfirmada(saved);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    public boolean anularTurno(Long id) {
        Optional<Turno> turnoOpt = repository.findById(id);
        if (turnoOpt.isPresent()) {
            Turno turno = turnoOpt.get();
            turno.setEstado("CANCELADO");
            Turno saved = repository.save(turno);
            eventPublisher.publicarCitaConfirmada(saved);
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
