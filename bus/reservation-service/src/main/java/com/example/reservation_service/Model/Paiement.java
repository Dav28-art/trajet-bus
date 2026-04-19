package com.example.reservation_service.Model;

public class Paiement {

    private double montant;

    public Paiement(Object montant) {
        this.montant = (double) montant;
    }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
}