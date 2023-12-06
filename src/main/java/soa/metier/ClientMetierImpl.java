package soa.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soa.entities.Facture;
import soa.entities.Client;
import soa.entities.Facture;
import soa.repository.ClientRepository;

import java.util.Collection;
import java.util.List;

@Service
public class ClientMetierImpl implements ClientMetierInterface {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void ajouterClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void updateClient(Long clientId, Client nouveauClient) {
        Client clientExistant = clientRepository.findById(clientId).orElse(null);
        if (clientExistant != null) {
            // Mettez à jour les champs du client existant avec les valeurs du nouveau client
            clientExistant.setNom(nouveauClient.getNom());
            clientExistant.setPrenom(nouveauClient.getPrenom());
            clientExistant.setAdresse(nouveauClient.getAdresse());
            // Mettez à jour d'autres champs selon vos besoins
            // Enregistrez les modifications dans la base de données
            clientRepository.save(clientExistant);
        }
    }

    @Override
    public Client rechercherClient(Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    @Override
    public List<Client> afficherListeClients() {
        return clientRepository.findAll();
    }

    @Override
    public void afficherHistoriqueAchatsClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            List<Facture> historiqueAchats = (List<Facture>) client.getFactures();
            for (Facture achat : historiqueAchats) {
                // Affichez les détails de chaque achat selon vos besoins
                System.out.println("ID de l'achat : " + achat.getId());
                System.out.println("Montant : " + achat.getMontant());
                System.out.println("Date d'achat : " + achat.getDateFacturation());
                // Affichez d'autres détails selon vos besoins
                System.out.println("-----");
            }
        }
    }

    @Override
    public void suivrePaiementClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            List<Facture> historiqueAchats = (List<Facture>) client.getFactures();

            for (Facture achat : historiqueAchats) {
                if (!achat.estPayee()) {
                    System.out.println("L'achat avec ID " + achat.getId() + " n'a pas été payé.");
                    // Autres actions de suivi des paiements, par exemple, envoyer des rappels de paiement, etc.
                } else {
                    System.out.println("L'achat avec ID " + achat.getId() + " a été payé.");
                }
            }
        }
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> rechercherParNom(String nom) {
            return clientRepository.findByNom(nom);
    }


    @Override
    public List<Client> rechercherParPrenom(String prenom) {
        return clientRepository.findByPrenom(prenom);
    }

}
