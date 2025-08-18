package com.tuempresa.medicos.controller;

import com.tuempresa.medicos.model.Medico;
import com.tuempresa.medicos.repository.MedicoRepository;
import com.tuempresa.medicos.service.MedicoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    private final MedicoService service;

    public MedicoController() {
        this.service = new MedicoService(new MedicoRepository());
    }

    @PostMapping
    public Medico crearMedico(@RequestBody Medico medico) {
        return service.crearMedico(medico);
    }

    @PutMapping("/{id}")
    public Optional<Medico> modificarMedico(@PathVariable Long id, @RequestBody Medico datos) {
        return service.modificarMedico(id, datos);
    }

    @DeleteMapping("/{id}")
    public void eliminarMedico(@PathVariable Long id) {
        service.eliminarMedico(id);
    }

    @GetMapping
    public List<Medico> listarMedicos() {
        return service.listarMedicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtenerMedico(@PathVariable Long id) {
        Optional<Medico> medicoOpt = service.obtenerMedico(id);
        if (medicoOpt.isPresent()) {
            return ResponseEntity.ok(medicoOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
