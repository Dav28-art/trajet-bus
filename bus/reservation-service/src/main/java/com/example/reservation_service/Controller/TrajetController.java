package com.example.reservation_service.Controller;

import com.example.reservation_service.Model.Trajet;
import com.example.reservation_service.Service.TrajetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trajets")
public class TrajetController {

    private final TrajetService trajetService;

    public TrajetController(TrajetService trajetService) {
        this.trajetService = trajetService;
    }

    // GET /api/trajets
    @GetMapping
    public ResponseEntity<List<Trajet>> getAllTrajets() {
        return ResponseEntity.ok(trajetService.getAllTrajets());
    }

    // GET /api/trajets/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Trajet> getTrajetById(@PathVariable String id) {
        return ResponseEntity.ok(trajetService.getTrajetById(id));
    }

    // GET /api/trajets/recherche?villeDepart=X&villeArrivee=Y&dateDepart=Z
    @GetMapping("/recherche")
    public ResponseEntity<List<Trajet>> rechercherTrajets(
            @RequestParam(required = false) String villeDepart,
            @RequestParam(required = false) String villeArrivee,
            @RequestParam(required = false) String dateDepart) {
        return ResponseEntity.ok(trajetService.rechercherTrajets(villeDepart, villeArrivee, dateDepart));
    }

    // POST /api/trajets
    @PostMapping
    public ResponseEntity<Trajet> createTrajet(@RequestBody Trajet trajet) {
        Trajet created = trajetService.createTrajet(trajet);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/trajets/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Trajet> updateTrajet(
            @PathVariable String id,
            @RequestBody Trajet trajetDetails) {
        return ResponseEntity.ok(trajetService.updateTrajet(id, trajetDetails));
    }

    // DELETE /api/trajets/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajet(@PathVariable String id) {
        trajetService.deleteTrajet(id);
        return ResponseEntity.noContent().build();
    }
}