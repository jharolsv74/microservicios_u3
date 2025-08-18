package com.tuempresa.medicos.service;

import com.tuempresa.medicos.model.Medico;
import com.tuempresa.medicos.repository.MedicoRepository;
import java.util.List;
import java.util.Optional;

public class MedicoService {
    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    public Medico crearMedico(Medico medico) {
        return repository.save(medico);
    }

    public Optional<Medico> modificarMedico(Long id, Medico datos) {
        Optional<Medico> medicoOpt = repository.findById(id);
        if (medicoOpt.isPresent()) {
            Medico medico = medicoOpt.get();
            medico.setNombre(datos.getNombre());
            medico.setEspecialidad(datos.getEspecialidad());
            medico.setDisponible(datos.isDisponible());
            return Optional.of(repository.save(medico));
        }
        return Optional.empty();
    }

    public boolean eliminarMedico(Long id) {
        Optional<Medico> medicoOpt = repository.findById(id);
        if (medicoOpt.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Medico> listarMedicos() {
        return repository.findAll();
    }

    public Optional<Medico> obtenerMedico(Long id) {
        return repository.findById(id);
    }
}
