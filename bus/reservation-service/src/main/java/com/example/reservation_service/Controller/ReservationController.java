package com.example.reservation_service.Controller;

import com.example.reservation_service.Model.Reservation;
import com.example.reservation_service.Service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    // ✅ Une seule déclaration, injection par constructeur
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Vérifier si un utilisateur existe via auth-service
    @GetMapping("/utilisateurs/{id}")
    public ResponseEntity<Boolean> verifierUtilisateur(@PathVariable String id) {
        return ResponseEntity.ok(reservationService.verifierUtilisateur(id));
    }

    // GET /api/reservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    // GET /api/reservations/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    // GET /api/reservations/utilisateur/{utilisateurId}
    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<Reservation>> getByUtilisateur(@PathVariable String utilisateurId) {
        return ResponseEntity.ok(reservationService.getReservationsByUtilisateur(utilisateurId));
    }

    // GET /api/reservations/trajet/{trajetId}
    @GetMapping("/trajet/{trajetId}")
    public ResponseEntity<List<Reservation>> getByTrajet(@PathVariable String trajetId) {
        return ResponseEntity.ok(reservationService.getReservationsByTrajet(trajetId));
    }

    // GET /api/reservations/statut/{statut}
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Reservation>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(reservationService.getReservationsByStatut(statut));
    }

    // Flux complet : auth-service → paiement-service → notification-service
    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody Reservation reservation) {
        try {
            //  vérifie l'utilisateur,  paie,  notifie
            String result = reservationService.createReservation(reservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (RuntimeException e) {
            // Utilisateur introuvable → 403, ou autre erreur → propagée
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    // PATCH /api/reservations/{id}/statut
    @PatchMapping("/{id}/statut")
    public ResponseEntity<Reservation> updateStatut(
            @PathVariable String id,
            @RequestParam String statut) {
        return ResponseEntity.ok(reservationService.updateStatut(id, statut));
    }

    // DELETE /api/reservations/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}