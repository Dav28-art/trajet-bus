package com.example.paiement_service.Repository;

import com.example.paiement_service.Model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, String> {

    List<Paiement> findByReservationId(String reservationId);

    List<Paiement> findByStatut(String statut);

    List<Paiement> findByMethode(String methode);

    List<Paiement> findByReservationIdAndStatut(String reservationId, String statut);
}