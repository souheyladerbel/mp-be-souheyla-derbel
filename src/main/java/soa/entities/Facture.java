package soa.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroFacture;
    private BigDecimal montant; // Corrected variable name
    private Date dateFacturation;

    private boolean estPayee;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Default constructor required for JPA
    public Facture() {
    }

    // Constructor with parameters
    public Facture(String numeroFacture, BigDecimal montant, Date dateFacturation, Client client) {
        this.numeroFacture = numeroFacture;
        this.montant = montant;
        this.dateFacturation = dateFacturation;
        this.client = client;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Date getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(Date dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public Client getClient() {
        return client;
    }
    public boolean estPayee() {
        return estPayee;
    }

    public void marquerCommePayee() {
        this.estPayee = true;
    }
    public void setClient(Client client) {
        this.client = client;
    }


    @Override
    public String toString() {
        return "Facture{" +
                "id=" + id +
                ", numeroFacture='" + numeroFacture + '\'' +
                ", montant=" + montant +
                ", dateFacturation=" + dateFacturation +
                ", client=" + client +
                '}';
    }
}
