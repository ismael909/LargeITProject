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
import lsit.Models.Clothes;

@Repository
public class GCSClothesRepository {

    private final String bucketName = "clothes-bucket";

    private final String prefix = "clothes/";

    private final Storage storage;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GCSClothesRepository() {
        this.storage = StorageOptions.getDefaultInstance().getService();
    }

    public void add(Clothes clothes) {
        try {

            clothes.setId(UUID.randomUUID());

            String clothesJson = objectMapper.writeValueAsString(clothes);

            BlobId blobId = BlobId.of(bucketName, prefix + clothes.getId().toString());

            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

            storage.create(blobInfo, clothesJson.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Clothes get(UUID id) {
        try {
            Blob blob = storage.get(BlobId.of(bucketName, prefix + id.toString()));
            if (blob == null) return null;

            String clothesJson = new String(blob.getContent());
            return objectMapper.readValue(clothesJson, Clothes.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void update(Clothes clothes) {
        this.add(clothes); // Overwrite the object
    }

    public void remove(UUID id) {
        storage.delete(BlobId.of(bucketName, prefix + id.toString()));
    }

    public List<Clothes> list() {
        List<Clothes> clothesList = new ArrayList<>();
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(prefix));

        for (Blob blob : blobs.iterateAll()) {
            try {
                String clothesJson = new String(blob.getContent());
                clothesList.add(objectMapper.readValue(clothesJson, Clothes.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clothesList;
    }
}
