package com.example.auth_service.Controller;

import com.example.auth_service.Model.Utilisateur;
import com.example.auth_service.Service.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    // Lombok génère le constructeur automatiquement
    private final UtilisateurService utilisateurService;

    // GET /api/utilisateurs
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAll() {
        return ResponseEntity.ok(utilisateurService.findAll());
    }

    // GET /api/utilisateurs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable String id) {
        return utilisateurService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/utilisateurs/email/{email}
    @GetMapping("/email/{email}")
    public ResponseEntity<Utilisateur> getByEmail(@PathVariable String email) {
        return utilisateurService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/utilisateurs/verify/{id}
    // Appelé par reservation-service pour vérifier si un utilisateur existe
    // Retourne true si trouvé, false sinon
    @GetMapping("/verify/{id}")
    public ResponseEntity<Boolean> verifierUtilisateur(@PathVariable String id) {
        // On passe par le service, jamais par le repository directement
        boolean existe = utilisateurService.findById(id).isPresent();
        return ResponseEntity.ok(existe);
    }

    // POST /api/utilisateurs
    @PostMapping
    public ResponseEntity<Utilisateur> create(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur saved = utilisateurService.save(utilisateur);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // PUT /api/utilisateurs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable String id,
                                               @RequestBody Utilisateur utilisateur) {
        try {
            return ResponseEntity.ok(utilisateurService.update(id, utilisateur));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/utilisateurs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            utilisateurService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}