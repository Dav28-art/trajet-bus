package com.example.reservation_service.Repository;

import com.example.reservation_service.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

    List<Reservation> findByUtilisateurId(String utilisateurId);

    List<Reservation> findByTrajetId(String trajetId);

    List<Reservation> findByStatut(String statut);

    Optional<Reservation> findByCleIdempotence(Object cleIdempotence);

    boolean existsByTrajetIdAndNumeroSiege(String trajetId, int numeroSiege);
}