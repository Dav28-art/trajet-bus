package com.example.reservation_service.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"trajetId", "numeroSiege"}),
    @UniqueConstraint(columnNames = {"cleIdempotence"})
})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

     @Column(nullable = false, unique = true)
    private String utilisateurId;

     @Column(nullable = false, unique = true)
    private String trajetId;

     @Column(nullable = false, unique = true)
    private int numeroSiege;

     @Column(nullable = false, unique = false)
    private String statut;

     @Column(nullable = false)
    private String cleIdempotence;

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
        Reservation other = (Reservation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Reservation [id=" + id + ", utilisateurId=" + utilisateurId + ", trajetId=" + trajetId
                + ", numeroSiege=" + numeroSiege + ", statut=" + statut + ", cleIdempotence=" + cleIdempotence + "]";
    }

    public Object getMontant() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMontant'");
    }
}