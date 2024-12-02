import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class S3ClientRepository {
    private static final String BUCKET = "lsit-bucket";
    private static final String CLIENTS_KEY = "clients.json";
    private final Storage storage;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Map<UUID, Client> clients = new HashMap<>();

    public S3ClientRepository() {
        try {
            storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build()
                    .getService();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize GCS client: " + e.getMessage(), e);
        }
    }

    public void loadClients() {
        try {
            Blob blob = storage.get(BlobId.of(BUCKET, CLIENTS_KEY));
            if (blob != null) {
                String json = new String(blob.getContent(), StandardCharsets.UTF_8);
                Client[] loadedClients = objectMapper.readValue(json, Client[].class);
                for (Client client : loadedClients) {
                    clients.put(client.id, client);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load clients: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveClients() {
        try {
            String clientsJson = objectMapper.writeValueAsString(clients.values());
            BlobId blobId = BlobId.of(BUCKET, CLIENTS_KEY);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/json").build();
            storage.create(blobInfo, clientsJson.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("Failed to save clients: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Client get(UUID clientId) {
        return clients.get(clientId);
    }

    public void add(Client client) {
        clients.put(client.id, client);
        saveClients();
    }

    public void remove(UUID clientId) {
        clients.remove(clientId);
        saveClients();
    }
}
