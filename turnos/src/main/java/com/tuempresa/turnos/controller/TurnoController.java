package com.tuempresa.turnos.controller;

import com.tuempresa.turnos.model.Turno;
import com.tuempresa.turnos.repository.TurnoRepository;
import com.tuempresa.turnos.service.TurnoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private final TurnoService service;

    public TurnoController(TurnoService service) {
        this.service = service;
    }

    @PostMapping
    public Turno agendarTurno(@RequestBody Turno turno) {
        return service.agendarTurno(turno);
    }

    @PutMapping("/{id}")
    public Optional<Turno> modificarTurno(@PathVariable Long id, @RequestBody Turno datos) {
        return service.modificarTurno(id, datos);
    }

    @DeleteMapping("/{id}")
    public void anularTurno(@PathVariable Long id) {
        service.anularTurno(id);
    }

    @GetMapping
    public List<Turno> listarTurnos() {
        return service.listarTurnos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> obtenerTurno(@PathVariable Long id) {
        Optional<Turno> turnoOpt = service.obtenerTurno(id);
        if (turnoOpt.isPresent()) {
            return ResponseEntity.ok(turnoOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
