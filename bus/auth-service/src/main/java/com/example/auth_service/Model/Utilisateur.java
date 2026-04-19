package com.example.auth_service.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Utilisateur")
@Entity
public class Utilisateur {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)  
    private String motDePasse;

    @Column(nullable = false, length = 100)
    private String nomComplet;

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;  
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Utilisateur)) return false;
        Utilisateur other = (Utilisateur) obj;
        return id != null && id.equals(other.id);  
    }


@Override
public String toString() {
  return "Utilisateur [id=" + id + ", email=" + email + ", motDePasse=" + motDePasse + ", nomComplet=" + nomComplet
      + "]";
}






}

