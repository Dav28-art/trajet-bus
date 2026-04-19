package com.example.notification_service.Repository;

import com.example.notification_service.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUtilisateurId(Long utilisateurId);

    List<Notification> findByStatut(String statut);

    List<Notification> findByType(String type);

    List<Notification> findByUtilisateurIdAndStatut(Long utilisateurId, String statut);
}