package com.tuempresa.medicos.repository;

import com.tuempresa.medicos.model.Medico;
import java.util.*;

public class MedicoRepository {
    private final Map<Long, Medico> medicos = new HashMap<>();
    private long idCounter = 1;

    public Medico save(Medico medico) {
        if (medico.getId() == null) {
            medico.setId(idCounter++);
        }
        medicos.put(medico.getId(), medico);
        return medico;
    }

    public Optional<Medico> findById(Long id) {
        return Optional.ofNullable(medicos.get(id));
    }

    public List<Medico> findAll() {
        return new ArrayList<>(medicos.values());
    }

    public void deleteById(Long id) {
        medicos.remove(id);
    }
}
