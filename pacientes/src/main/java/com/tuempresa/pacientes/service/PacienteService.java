package com.tuempresa.pacientes.service;

import com.tuempresa.pacientes.model.Paciente;
import com.tuempresa.pacientes.repository.PacienteRepository;
import java.util.List;
import java.util.Optional;

public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public Paciente crearPaciente(Paciente paciente) {
        return repository.save(paciente);
    }

    public Optional<Paciente> modificarPaciente(Long id, Paciente datos) {
        Optional<Paciente> pacienteOpt = repository.findById(id);
        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            paciente.setNombre(datos.getNombre());
            paciente.setApellido(datos.getApellido());
            paciente.setEmail(datos.getEmail());
            paciente.setTelefono(datos.getTelefono());
            return Optional.of(repository.save(paciente));
        }
        return Optional.empty();
    }

    public boolean eliminarPaciente(Long id) {
        Optional<Paciente> pacienteOpt = repository.findById(id);
        if (pacienteOpt.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Paciente> listarPacientes() {
        return repository.findAll();
    }

    public Optional<Paciente> obtenerPaciente(Long id) {
        return repository.findById(id);
    }
}
