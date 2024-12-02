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

import lsit.Models.Order;

@Repository
public class GCSOrderRepository {

    private final String bucketName = "clothes-bucket"; // Replace with your GCS bucket name
    private final String prefix = "orders/"; // Folder path for orders
    private final Storage storage;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GCSOrderRepository() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    // Add or update an order in GCS
    public void update(Order order) {
        try {
            if (order.getId() == null) {
                order.setId(UUID.randomUUID());
            }

            String orderJson = objectMapper.writeValueAsString(order);
            BlobId blobId = BlobId.of(bucketName, prefix + order.getClientId().toString());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, orderJson.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get an order by client ID
    public Order get(UUID clientId) {
        try {
            Blob blob = storage.get(BlobId.of(bucketName, prefix + clientId.toString()));
            if (blob == null) return null;

            String orderJson = new String(blob.getContent());
            return objectMapper.readValue(orderJson, Order.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Remove an order by client ID
    public void remove(UUID clientId) {
        storage.delete(BlobId.of(bucketName, prefix + clientId.toString()));
    }

    // List all orders
    public List<Order> list() {
        List<Order> orderList = new ArrayList<>();
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(prefix));

        for (Blob blob : blobs.iterateAll()) {
            try {
                String orderJson = new String(blob.getContent());
                orderList.add(objectMapper.readValue(orderJson, Order.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return orderList;
    }
}
