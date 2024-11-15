package lsit.Repositories;


import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lsit.Models.Clothes;

@Repository
public class ClothesRepository {
    static HashMap<UUID, Clothes> clothes = new HashMap<>();
    @PostConstruct
    public void initData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Clothes> clothesList = objectMapper.readValue(new File("clothes.json"),
                    new TypeReference<List<Clothes>>() {});
            clothesList.forEach(clothesItem -> clothes.put(clothesItem.id, clothesItem));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void add(Clothes p){
        p.id = UUID.randomUUID();
        clothes.put(p.id, p);
    }

    public Clothes get(UUID id){
        return clothes.get(id);
    }

    public void remove(UUID id){
        clothes.remove(id);
    }

    public void update(Clothes p) {
        Clothes x = clothes.get(p.id);
        if (x != null) {
            x.name = p.name;
            x.kind = p.kind;
            x.size = p.size;
        }
    }

    public List<Clothes> list(){
        return new ArrayList<>(clothes.values());
    }
}
