package com.tuempresa.pacientes.repository;

import com.tuempresa.pacientes.model.Paciente;
import java.util.*;

public class PacienteRepository {
    private final Map<Long, Paciente> pacientes = new HashMap<>();
    private long idCounter = 1;

    public Paciente save(Paciente paciente) {
        if (paciente.getId() == null) {
            paciente.setId(idCounter++);
        }
        pacientes.put(paciente.getId(), paciente);
        return paciente;
    }

    public Optional<Paciente> findById(Long id) {
        return Optional.ofNullable(pacientes.get(id));
    }

    public List<Paciente> findAll() {
        return new ArrayList<>(pacientes.values());
    }

    public void deleteById(Long id) {
        pacientes.remove(id);
    }
}
