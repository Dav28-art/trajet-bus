package com.example.paiement_service.Controller;

import com.example.paiement_service.Model.Paiement;
import com.example.paiement_service.Service.PaiementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    // ✅ Logger à la place de System.out.println
    private static final Logger log = LoggerFactory.getLogger(PaiementController.class);

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    // GET /api/paiements
    @GetMapping
    public ResponseEntity<List<Paiement>> getAllPaiements() {
        return ResponseEntity.ok(paiementService.getAllPaiements());
    }

    // GET /api/paiements/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable String id) {
        return ResponseEntity.ok(paiementService.getPaiementById(id));
    }

    // GET /api/paiements/reservation/{reservationId}
    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<Paiement>> getByReservation(@PathVariable String reservationId) {
        return ResponseEntity.ok(paiementService.getPaiementsByReservation(reservationId));
    }

    // GET /api/paiements/statut/{statut}
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Paiement>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(paiementService.getPaiementsByStatut(statut));
    }

    // POST /api/paiements — ✅ un seul @PostMapping qui gère création + confirmation
    @PostMapping
    public ResponseEntity<Paiement> createPaiement(@RequestBody Paiement paiement) {
        // ✅ Log propre à la place de System.out.println
        log.info("Paiement reçu pour le montant : {}", paiement.getMontant());

        Paiement created = paiementService.createPaiement(paiement);

        log.info("Paiement accepté avec l'id : {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PATCH /api/paiements/{id}/statut
    @PatchMapping("/{id}/statut")
    public ResponseEntity<Paiement> updateStatut(
            @PathVariable String id,
            @RequestParam String statut) {
        return ResponseEntity.ok(paiementService.updateStatut(id, statut));
    }

    // DELETE /api/paiements/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable String id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
}