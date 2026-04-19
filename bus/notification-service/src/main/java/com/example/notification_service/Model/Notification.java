package com.example.notification_service.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Table(name = "Notification")
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private Long utilisateurId;

    @Column()
    private String type; // SMS, EMAIL

    @Column(nullable = false, unique = false, length = 255)
    private String message;

    @Column(nullable = false, unique = false, length = 100)
    private String statut;

    @Column(nullable = false, unique = false)
    private Date dateCreation;

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
        Notification other = (Notification) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Notification [id=" + id + ", utilisateurId=" + utilisateurId + ", type=" + type
                + ", message=" + message + ", statut=" + statut + ", dateCreation=" + dateCreation + "]";
    }

}