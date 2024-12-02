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
import java.util.UUID;

@Repository
public class S3BasketRepository {
    private static final String BUCKET = "lsit-bucket";
    private static final String BASKET_KEY = "basket.json";
    private final Storage storage;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Basket basket = new Basket();

    public S3BasketRepository() {
        try {
            storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build()
                    .getService();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize GCS client: " + e.getMessage(), e);
        }
    }

    public Basket getBasket() {
        try {
            Blob blob = storage.get(BlobId.of(BUCKET, BASKET_KEY));
            if (blob != null) {
                String json = new String(blob.getContent(), StandardCharsets.UTF_8);
                basket = objectMapper.readValue(json, Basket.class);
            }
        } catch (Exception e) {
            System.err.println("Failed to load basket: " + e.getMessage());
            e.printStackTrace();
        }
        return basket;
    }

    public void saveBasket() {
        try {
            String basketJson = objectMapper.writeValueAsString(basket);
            BlobId blobId = BlobId.of(BUCKET, BASKET_KEY);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/json").build();
            storage.create(blobInfo, basketJson.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.err.println("Failed to save basket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addToBasket(UUID clothesId) {
        basket.addClothes(clothesId);
        saveBasket();
    }

    public void removeFromBasket(UUID clothesId) {
        basket.removeClothes(clothesId);
        saveBasket();
    }
}
