package com.example.paiement_service.Service;

import com.example.paiement_service.Model.Paiement;
import com.example.paiement_service.Repository.PaiementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;

    public PaiementService(PaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    public Paiement getPaiementById(String id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable avec l'id : " + id));
    }

    public List<Paiement> getPaiementsByReservation(String reservationId) {
        return paiementRepository.findByReservationId(reservationId);
    }

    public List<Paiement> getPaiementsByStatut(String statut) {
        return paiementRepository.findByStatut(statut);
    }

    public Paiement createPaiement(Paiement paiement) {
        paiement.setStatut("EN_ATTENTE");
        return paiementRepository.save(paiement);
    }

    public Paiement updateStatut(String id, String statut) {
        Paiement paiement = getPaiementById(id);
        paiement.setStatut(statut);
        return paiementRepository.save(paiement);
    }

    public void deletePaiement(String id) {
        if (!paiementRepository.existsById(id)) {
            throw new RuntimeException("Paiement introuvable avec l'id : " + id);
        }
        paiementRepository.deleteById(id);
    }
}