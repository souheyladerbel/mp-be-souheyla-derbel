package soa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soa.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    // Recherche un client par son nom
    List<Client> findByNom(String nom);
    List<Client> findByPrenom(String prenom);

    Optional<Client> findByNomAndPrenom(String nom, String prenom);


    // Recherche des clients par une adresse donnée



}
