package soa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import soa.entities.Client;
import soa.entities.Facture;
import soa.repository.ClientRepository;
import soa.repository.FactureRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJpaApplication.class, args);
    }

    @Bean
    public CommandLineRunner anotherDemo(ClientRepository clientRepository, FactureRepository factureRepository) {
        return (args) -> {
            // Ajouter des exemples de données ici pour Client
            Client client1 = new Client("souheyla", "Derbel", "24634008", "Mahres");
            Client client2 = new Client("Nour", "Karray", "001525", "route tenyour");

            // Enregistrez les clients dans la base de données
            List<Client> savedClients = clientRepository.saveAll(List.of(client1, client2));

            // Utilisez les clients enregistrés pour créer des factures
            List<Facture> facturesClient1 = List.of(
                    new Facture("s01", BigDecimal.valueOf(1000.0), new Date(), savedClients.get(0)),
                    new Facture("F002", BigDecimal.valueOf(1500.0), new Date(), savedClients.get(0))
            );

            List<Facture> facturesClient2 = List.of(
                    new Facture("F003", BigDecimal.valueOf(2000.0), new Date(), savedClients.get(1)),
                    new Facture("F004", BigDecimal.valueOf(3000.0), new Date(), savedClients.get(1))
            );

            // Enregistrez les factures dans la base de données
            factureRepository.saveAll(facturesClient1);
            factureRepository.saveAll(facturesClient2);

            // Marquez une facture pour le client1 comme payée
            facturesClient1.get(0).marquerCommePayee();

            // Enregistrez à nouveau la facture mise à jour dans la base de données
            factureRepository.save(facturesClient1.get(0));
        };
    }
}
