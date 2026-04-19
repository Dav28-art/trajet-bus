package com.example.notification_service.Controller;

import com.example.notification_service.Model.Notification;
import com.example.notification_service.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    // ✅ Logger à la place de System.out.println
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // GET /api/notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // GET /api/notifications/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    // GET /api/notifications/utilisateur/{utilisateurId}
    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<Notification>> getByUtilisateur(@PathVariable Long utilisateurId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUtilisateur(utilisateurId));
    }

    // GET /api/notifications/statut/{statut}
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Notification>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(notificationService.getNotificationsByStatut(statut));
    }

    // POST /api/notifications — ✅ un seul @PostMapping qui gère création + envoi
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        // ✅ Log propre à la place de System.out.println
        log.info("Notification reçue : {}", notification.getMessage());

        Notification created = notificationService.createNotification(notification);

        log.info("Notification envoyée avec l'id : {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PATCH /api/notifications/{id}/statut
    @PatchMapping("/{id}/statut")
    public ResponseEntity<Notification> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        return ResponseEntity.ok(notificationService.updateStatut(id, statut));
    }

    // DELETE /api/notifications/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}