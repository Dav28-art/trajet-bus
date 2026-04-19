package com.example.notification_service.Service;

import com.example.notification_service.Model.Notification;
import com.example.notification_service.Repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable avec l'id : " + id));
    }

    public List<Notification> getNotificationsByUtilisateur(Long utilisateurId) {
        return notificationRepository.findByUtilisateurId(utilisateurId);
    }

    public List<Notification> getNotificationsByStatut(String statut) {
        return notificationRepository.findByStatut(statut);
    }

    public Notification createNotification(Notification notification) {
        notification.setDateCreation(new Date());
        notification.setStatut("EN_ATTENTE");
        return notificationRepository.save(notification);
    }

    public Notification updateStatut(Long id, String statut) {
        Notification notification = getNotificationById(id);
        notification.setStatut(statut);
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification introuvable avec l'id : " + id);
        }
        notificationRepository.deleteById(id);
    }
}