package soa.metier;

import soa.entities.Client;
import soa.entities.Facture;

import java.util.Collection;
import java.util.List;

public interface ClientMetierInterface {
    Client saveClient(Client client);

    void ajouterClient(Client client);

    void updateClient(Long clientId, Client nouveauClient);

    Client rechercherClient(Long clientId);

    List<Client> afficherListeClients();

    void afficherHistoriqueAchatsClient(Long clientId);

    void suivrePaiementClient(Long clientId);

    Client getClientById(Long id);

    void deleteClient(Long id);
    List<Client> rechercherParNom(String nom);
    List<Client>  rechercherParPrenom(String prenom) ;




}
