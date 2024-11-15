package lsit.Controllers;

import java.util.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lsit.Models.Clothes;
import lsit.Repositories.ClothesRepository;

@RestController
public class ClothesController {

    ClothesRepository ClothesRepository;

    public ClothesController(ClothesRepository ClothesRepository){
        this.ClothesRepository = ClothesRepository;
    }

    @GetMapping("/clothes")
    public List<Clothes> list(){
        return ClothesRepository.list();
    }

    @GetMapping("/clothes/{id}")
    public Clothes get(@PathVariable("id") UUID id){
        return ClothesRepository.get(id);
    }

    @PostMapping("/clothes")
    public Clothes add(@RequestBody Clothes p){
        ClothesRepository.add(p);
        return p;
    }

    @PutMapping("/clothes/{id}")
    public Clothes update(@PathVariable("id") UUID id, @RequestBody Clothes p){
        p.id = id;
        ClothesRepository.update(p);
        return p;
    }

    @DeleteMapping("/clothes/{id}")
    public void delete(@PathVariable("id") UUID id){
        ClothesRepository.remove(id);
    }
}
