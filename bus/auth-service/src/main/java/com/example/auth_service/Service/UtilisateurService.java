package com.example.auth_service.Service;

import com.example.auth_service.Model.Utilisateur;
import com.example.auth_service.Repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> findById(String id) {
        return utilisateurRepository.findById(id);
    }

    public Optional<Utilisateur> findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Utilisateur save(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà.");
        }
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur update(String id, Utilisateur updated) {
        return utilisateurRepository.findById(id).map(existing -> {
            existing.setEmail(updated.getEmail());
            existing.setMotDePasse(updated.getMotDePasse());
            existing.setNomComplet(updated.getNomComplet());
            return utilisateurRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id : " + id));
    }

    public void deleteById(String id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable avec l'id : " + id);
        }
        utilisateurRepository.deleteById(id);
    }
}