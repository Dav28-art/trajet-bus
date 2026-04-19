package com.example.reservation_service.Service;

import com.example.reservation_service.Model.Notification;
import com.example.reservation_service.Model.Paiement;
import com.example.reservation_service.Model.Reservation;
import com.example.reservation_service.Repository.ReservationRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestTemplate restTemplate;

    public ReservationService(ReservationRepository reservationRepository,
                              RestTemplate restTemplate) {
        this.reservationRepository = reservationRepository;
        this.restTemplate = restTemplate;
    }

    
    // Vérifier si un utilisateur existe via auth-service
    
    public boolean verifierUtilisateur(String utilisateurId) {
        // ✅ URL corrigée — correspond au @GetMapping("/verify/{id}") du UtilisateurController
        String authUrl = "http://auth-service/api/utilisateurs/verify/" + utilisateurId;

        // Récupère true/false depuis auth-service
        Boolean existe = restTemplate.getForObject(authUrl, Boolean.class);

        // Si null (service indisponible) on considère l'utilisateur comme invalide
        return Boolean.TRUE.equals(existe);
    }

    
    // GET
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(String id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable avec l'id : " + id));
    }

    public List<Reservation> getReservationsByUtilisateur(String utilisateurId) {
        return reservationRepository.findByUtilisateurId(utilisateurId);
    }

    public List<Reservation> getReservationsByTrajet(String trajetId) {
        return reservationRepository.findByTrajetId(trajetId);
    }

    public List<Reservation> getReservationsByStatut(String statut) {
        return reservationRepository.findByStatut(statut);
    }

    
    // POST — Flux complet : auth → création → paiement → notification
    public String createReservation(Reservation reservation) {

        //  Validation
        if (reservation.getUtilisateurId() == null || reservation.getUtilisateurId().isBlank()) {
            throw new RuntimeException("L'identifiant de l'utilisateur est obligatoire.");
        }

        // Vérification utilisateur via auth-service
        if (!verifierUtilisateur(reservation.getUtilisateurId())) {
            throw new RuntimeException("Utilisateur introuvable ou non autorisé !");
        }

        // Idempotence — évite les doubles réservations
        if (reservation.getCleIdempotence() != null) {
            Optional<Reservation> existing = reservationRepository
                    .findByCleIdempotence(reservation.getCleIdempotence());
            if (existing.isPresent()) {
                return "Réservation déjà existante : " + existing.get().getId();
            }
        }

        //  Vérifier si le siège est déjà pris
        if (reservationRepository.existsByTrajetIdAndNumeroSiege(
                reservation.getTrajetId(), reservation.getNumeroSiege())) {
            throw new RuntimeException("Le siège " + reservation.getNumeroSiege()
                    + " est déjà réservé pour ce trajet.");
        }

        // Sauvegarder la réservation en base
        reservation.setStatut("CONFIRMEE");
        reservationRepository.save(reservation);

        //  Appel paiement-service via HTTP POST
        String paiementUrl = "http://paiement-service/api/paiements";
        restTemplate.postForObject(
            paiementUrl,
            new Paiement(reservation.getMontant()),
            String.class
        );

        // Appel notification-service via HTTP POST
        String notifUrl = "http://notification-service/api/notifications";
        restTemplate.postForObject(
            notifUrl,
            new Notification("Réservation confirmée, paiement de : " + reservation.getMontant()),
            String.class
        );

        return "Réservation confirmée pour l'utilisateur : " + reservation.getUtilisateurId();
    }

    // PATCH
    public Reservation updateStatut(String id, String statut) {
        Reservation reservation = getReservationById(id);
        reservation.setStatut(statut);
        return reservationRepository.save(reservation);
    }

    
    // DELETE
    public void deleteReservation(String id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Réservation introuvable avec l'id : " + id);
        }
        reservationRepository.deleteById(id);
    }
}