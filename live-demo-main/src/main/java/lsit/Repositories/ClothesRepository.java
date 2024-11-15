package lsit.Repositories;

import java.util.*;

import org.springframework.stereotype.Repository;

import lsit.Models.Clothes;

@Repository
public class ClothesRepository {
    static HashMap<UUID, Clothes> clothes = new HashMap<>();

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

    public void update(Clothes p){
        Clothes x = clothes.get(p.id);
        x.name = p.name;
        x.kind = p.kind;
    }

    public List<Clothes> list(){
        return new ArrayList<>(clothes.values());
    }
}
