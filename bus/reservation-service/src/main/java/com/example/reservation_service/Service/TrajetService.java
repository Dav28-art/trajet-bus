package com.example.reservation_service.Service;

import com.example.reservation_service.Model.Trajet;
import com.example.reservation_service.Repository.TrajetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrajetService {

    private final TrajetRepository trajetRepository;

    public TrajetService(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    public List<Trajet> getAllTrajets() {
        return trajetRepository.findAll();
    }

    public Trajet getTrajetById(String id) {
        return trajetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trajet introuvable avec l'id : " + id));
    }

    public List<Trajet> rechercherTrajets(String villeDepart, String villeArrivee, String dateDepart) {
        if (villeDepart != null && villeArrivee != null && dateDepart != null) {
            return trajetRepository.findByVilleDepartAndVilleArriveeAndDateDepart(
                    villeDepart, villeArrivee, dateDepart);
        } else if (villeDepart != null && villeArrivee != null) {
            return trajetRepository.findByVilleDepartAndVilleArrivee(villeDepart, villeArrivee);
        } else if (villeDepart != null) {
            return trajetRepository.findByVilleDepart(villeDepart);
        } else if (villeArrivee != null) {
            return trajetRepository.findByVilleArrivee(villeArrivee);
        }
        return trajetRepository.findAll();
    }

    public Trajet createTrajet(Trajet trajet) {
        return trajetRepository.save(trajet);
    }

    public Trajet updateTrajet(String id, Trajet trajetDetails) {
        Trajet trajet = getTrajetById(id);
        trajet.setVilleDepart(trajetDetails.getVilleDepart());
        trajet.setVilleArrivee(trajetDetails.getVilleArrivee());
        trajet.setDateDepart(trajetDetails.getDateDepart());
        trajet.setNombreSiegesTotal(trajetDetails.getNombreSiegesTotal());
        trajet.setPrix(trajetDetails.getPrix());
        return trajetRepository.save(trajet);
    }

    public void deleteTrajet(String id) {
        if (!trajetRepository.existsById(id)) {
            throw new RuntimeException("Trajet introuvable avec l'id : " + id);
        }
        trajetRepository.deleteById(id);
    }
}