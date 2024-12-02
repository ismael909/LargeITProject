package lsit.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lsit.Models.Client;

@Repository
public class GCSClientRepository {

    private final String bucketName = "clothes-bucket"; // Replace with your bucket name
    private final String prefix = "clients/"; // Folder path for client data
    private final Storage storage;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GCSClientRepository() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    // Add a new client to GCS
    public void add(Client client) {
        try {
            client.setId(UUID.randomUUID()); // Generate a unique ID for the client
            String clientJson = objectMapper.writeValueAsString(client);

            BlobId blobId = BlobId.of(bucketName, prefix + client.getId().toString());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, clientJson.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get a client by ID
    public Client get(UUID id) {
        try {
            Blob blob = storage.get(BlobId.of(bucketName, prefix + id.toString()));
            if (blob == null) return null;

            String clientJson = new String(blob.getContent());
            return objectMapper.readValue(clientJson, Client.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update an existing client
    public void update(Client client) {
        this.add(client); // Overwrite the object
    }

    // Remove a client by ID
    public void remove(UUID id) {
        storage.delete(BlobId.of(bucketName, prefix + id.toString()));
    }

    // List all clients
    public List<Client> list() {
        List<Client> clientList = new ArrayList<>();
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(prefix));

        for (Blob blob : blobs.iterateAll()) {
            try {
                String clientJson = new String(blob.getContent());
                clientList.add(objectMapper.readValue(clientJson, Client.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientList;
    }
}
