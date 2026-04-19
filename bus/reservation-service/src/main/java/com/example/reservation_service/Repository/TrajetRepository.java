package com.example.reservation_service.Repository;

import com.example.reservation_service.Model.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, String> {

    List<Trajet> findByVilleDepart(String villeDepart);

    List<Trajet> findByVilleArrivee(String villeArrivee);

    List<Trajet> findByVilleDepartAndVilleArrivee(String villeDepart, String villeArrivee);

    List<Trajet> findByVilleDepartAndVilleArriveeAndDateDepart(
            String villeDepart, String villeArrivee, String dateDepart);
}