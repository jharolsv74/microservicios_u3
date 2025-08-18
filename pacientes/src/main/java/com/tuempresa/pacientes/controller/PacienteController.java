package com.tuempresa.pacientes.controller;

import com.tuempresa.pacientes.model.Paciente;
import com.tuempresa.pacientes.repository.PacienteRepository;
import com.tuempresa.pacientes.service.PacienteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService service;

    public PacienteController() {
        this.service = new PacienteService(new PacienteRepository());
    }

    @PostMapping
    public Paciente crearPaciente(@RequestBody Paciente paciente) {
        return service.crearPaciente(paciente);
    }

    @PutMapping("/{id}")
    public Optional<Paciente> modificarPaciente(@PathVariable Long id, @RequestBody Paciente datos) {
        return service.modificarPaciente(id, datos);
    }

    @DeleteMapping("/{id}")
    public void eliminarPaciente(@PathVariable Long id) {
        service.eliminarPaciente(id);
    }

    @GetMapping
    public List<Paciente> listarPacientes() {
        return service.listarPacientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPaciente(@PathVariable Long id) {
        Optional<Paciente> pacienteOpt = service.obtenerPaciente(id);
        if (pacienteOpt.isPresent()) {
            return ResponseEntity.ok(pacienteOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
