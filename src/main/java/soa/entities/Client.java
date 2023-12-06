package soa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String numero;
    private String adresse;
    private boolean actif;
    private BigDecimal MontantF;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Facture> factures;

    // Constructeurs

    public Client() {
        // Constructeur par défaut nécessaire pour JPA
    }

    public Client(String nom, String prenom, String numero, String adresse ) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.adresse = adresse;
        this.actif= true;
        this.factures = new ArrayList<>();
        this.MontantF= BigDecimal.ZERO;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }

    private boolean isFactureDansDerniereAnnee(Facture facture) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1); // Soustraire une année à la date actuelle
        Date derniereAnnee = calendar.getTime();
        return facture.getDateFacturation().after(derniereAnnee);
    }

    public void AllFacture() {
        if (factures != null && !factures.isEmpty()) {
            Facture derniereFacture = factures.get(factures.size() - 1);
            actif = isFactureDansDerniereAnnee(derniereFacture);

            // If the client is active, update the MontantF
            if (actif) {
                MontantF = factures.stream()
                        .map(Facture::getMontant)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            } else {
                MontantF = BigDecimal.ZERO;
            }
        } else {
            actif = false;
            MontantF = BigDecimal.ZERO;
        }
    }
    public boolean aPayeToutesLesFactures() {
        if (factures != null && !factures.isEmpty()) {
            for (Facture facture : factures) {
                if (!facture.estPayee()) {
                    return false; // Si au moins une facture n'est pas payée, retourner false
                }
            }
            return true; // Toutes les factures sont payées
        }
        return false; // Aucune facture
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numero='" + numero + '\'' +
                ", adresse='" + adresse + '\'' +
                ", factures=" + factures +
                '}';
    }
}
