package com.example.reservation_service.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

     @Column(nullable = false, unique = false)
    private String villeDepart;

     @Column(nullable = false, unique = false)
    private String villeArrivee;

     @Column(nullable = false, unique = false)
    private String dateDepart;

     @Column(nullable = false, unique = false)
    private int nombreSiegesTotal;

     @Column(nullable = false, unique = false)
    private int prix;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Trajet other = (Trajet) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Trajet [id=" + id + ", villeDepart=" + villeDepart + ", villeArrivee=" + villeArrivee
                + ", dateDepart=" + dateDepart + ", nombreSiegesTotal=" + nombreSiegesTotal + ", prix=" + prix + "]";
    }
}