package lsit.Repositories;

import lsit.Models.Client;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;
@Repository
public class ClientRepository {
    private Map<UUID, Client> clients = new HashMap<>();
    public ClientRepository() {
        // Initialize with some test data
        initializeClients();
            }
            private void initializeClients() {
                // Sample client initialization
                UUID clientId = UUID.randomUUID();
                Client sampleClient = new Client(clientId, "Achilles", 150); // Example client with 150 credits
                add(sampleClient);
                System.out.println("Initialized client: " + sampleClient.name + " with ID " + clientId + " and credits " + sampleClient.credit);
            }
            public void add(Client client) {
        clients.put(client.id, client);
    }

    public Client get(UUID clientId) {
        return clients.get(clientId);
    }

    public void update(Client client) {
        clients.put(client.id, client);
    }

    public void remove(UUID clientId) {
        clients.remove(clientId);
    }
}
