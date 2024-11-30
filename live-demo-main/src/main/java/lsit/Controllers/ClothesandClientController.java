// package lsit.Controllers;

// import java.util.UUID;

// import org.springframework.http.ResponseEntity;

// import org.springframework.web.bind.annotation.DeleteMapping;

// import org.springframework.web.bind.annotation.GetMapping;

// import org.springframework.web.bind.annotation.PathVariable;

// import org.springframework.web.bind.annotation.PostMapping;

// import org.springframework.web.bind.annotation.RequestMapping;

// import org.springframework.web.bind.annotation.RestController;

// import lsit.Models.Basket;

// import lsit.Models.Client;

// import lsit.Models.Clothes;

// import lsit.Repositories.BasketRepository;

// import lsit.Repositories.ClientRepository;

// import lsit.Repositories.ClothesRepository;

// @RestController

// @RequestMapping("/basket")

// public class ClothesandClientController {

//     private final ClothesRepository clothesRepository;

//     private final BasketRepository basketRepository;

//     private final ClientRepository clientRepository;

//     public ClothesandClientController(ClothesRepository clothesRepository, BasketRepository basketRepository, ClientRepository clientRepository) {

//         this.clothesRepository = clothesRepository;

//         this.basketRepository = basketRepository;

//         this.clientRepository = clientRepository;

//     }

//     @PostMapping("/add/{clothesId}/{clientId}")
    
//     public ResponseEntity<String> addToBasket(@PathVariable UUID clothesId, @PathVariable UUID clientId) {

//         Clothes clothes = clothesRepository.get(clothesId);

//         Client client = clientRepository.get(clientId);

//         if (clothes == null) {

//             return ResponseEntity.badRequest().body("Clothes item not found.");

//         }

//         if (client == null) {

//             return ResponseEntity.badRequest().body("Client not found.");

//         }

//         basketRepository.addToBasket(clothesId);

//         return ResponseEntity.ok("Item added to basket.");

//     }

//     @DeleteMapping("/remove/{clothesId}")

//     public ResponseEntity<String> removeFromBasket(@PathVariable UUID clothesId) {

//         basketRepository.removeFromBasket(clothesId);

//         return ResponseEntity.ok("Item removed from basket.");

//     }

//     @GetMapping

//     public ResponseEntity<Basket> getBasket() {

//         Basket basket = basketRepository.getBasket();

//         if (basket == null || basket.clothesIds.isEmpty()) {

//             return ResponseEntity.ok(new Basket());

//         }

//         return ResponseEntity.ok(basket);
//     }
// }

