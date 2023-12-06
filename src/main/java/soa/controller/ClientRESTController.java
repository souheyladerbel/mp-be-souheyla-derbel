package soa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soa.entities.Client;
import soa.entities.Facture;
import soa.metier.ClientMetierImpl;
import soa.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:64792/")
@RequestMapping("/clients")
public class ClientRESTController {

    @Autowired
    private ClientRepository clientRepository;
    private ClientMetierImpl clientMetier;

    // Welcome message
    @GetMapping("/index")
    public String accueil() {
        return "Bienvenue au service Web REST 'clients'.....";
    }

    // Get all clients
    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get a client by specifying its 'id'
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public Client getClient(@PathVariable Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    // Delete a client by 'id' using the 'GET' method
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return ResponseEntity.ok("La suppression est valide pour l'ID : " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé pour l'ID : " + id);
        }
    }

    // Add a client using the "POST" method
    @PostMapping(value = "/Save", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> saveClient(@RequestBody Client client) {
        // Vérifier si le client existe déjà
        Optional<Client> existingClient = clientRepository.findByNomAndPrenom(client.getNom(), client.getPrenom());

        if (existingClient.isPresent()) {
            // Le client existe déjà, renvoyer une réponse conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le client existe déjà.");
        } else {
            // Le client n'existe pas, ajouter le client à la base de données
            Client savedClient = clientRepository.save(client);
            return ResponseEntity.ok(savedClient);
        }
    }
    // Update a client using the "PUT" method
    /*@PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Client existingClient = clientRepository.findById(id).orElse(null);
        if (existingClient != null) {
            existingClient.setNom(updatedClient.getNom());
            existingClient.setPrenom(updatedClient.getPrenom());
            existingClient.setNumero(updatedClient.getNumero());
            existingClient.setAdresse(updatedClient.getAdresse());
            Client updated = clientRepository.save(existingClient);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            Client existingClient = optionalClient.get();

            // Mettez à jour les propriétés du client existant avec les nouvelles valeurs
            existingClient.setNom(updatedClient.getNom());
            existingClient.setPrenom(updatedClient.getPrenom());
            existingClient.setNumero(updatedClient.getNumero());
            existingClient.setAdresse(updatedClient.getAdresse());
            // Enregistrez les modifications dans la base de données
            clientRepository.save(existingClient);

            return new ResponseEntity<>(existingClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Delete a client using the 'DELETE' method
    @DeleteMapping("/")
    public void deleteClient(@RequestBody Client client) {
        clientRepository.delete(client);
    }
    @GetMapping("/searchN")
    public ResponseEntity<List<Client>> getClientsByNom(@RequestParam String nom) {
        List<Client> matchingClients = clientRepository.findByNom(nom);

        if (!matchingClients.isEmpty()) {
            return new ResponseEntity<>(matchingClients, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{clientId}/checkPayments")
    public ResponseEntity<String> checkPayments(@PathVariable Long clientId) {
        // Retrieve the client from the database
        Optional<Client> optionalClient = clientRepository.findById(clientId);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            // Check if the client has at least one unpaid invoice
            boolean hasUnpaidInvoice = client.getFactures().stream()
                    .anyMatch(facture -> !facture.estPayee());

            return ResponseEntity.ok(hasUnpaidInvoice ? "false" : "true");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé pour l'ID : " + clientId);
        }
    }
    @GetMapping("/searchP")
    public ResponseEntity<List<Client>> getClientsByPrenom(@RequestParam String prenom) {
        List<Client> matchingClients = clientRepository.findByPrenom(prenom);

        if (!matchingClients.isEmpty()) {
            return new ResponseEntity<>(matchingClients, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/Active")
    public ResponseEntity<String> getClientStatutActivite(@PathVariable Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.AllFacture(); // Assurez-vous que le chiffre d'affaires est à jour
            String statutActivite = client.isActif() ? "Actif" : "Inactif";
            return new ResponseEntity<>(statutActivite, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
