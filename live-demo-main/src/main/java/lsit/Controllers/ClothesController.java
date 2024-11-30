// package lsit.Controllers;

// import java.util.List;

// import java.util.UUID;

// import org.springframework.http.ResponseEntity;

// import org.springframework.web.bind.annotation.DeleteMapping;

// import org.springframework.web.bind.annotation.GetMapping;

// import org.springframework.web.bind.annotation.PathVariable;

// import org.springframework.web.bind.annotation.PostMapping;

// import org.springframework.web.bind.annotation.PutMapping;

// import org.springframework.web.bind.annotation.RequestBody;

// import org.springframework.web.bind.annotation.RestController;

// import lsit.Models.Basket;

// import lsit.Models.Client;

// import lsit.Models.Clothes;

// import lsit.Repositories.BasketRepository;

// import lsit.Repositories.ClientRepository;

// import lsit.Repositories.ClothesRepository;

// @RestController
// public class ClothesController {

// private  ClothesRepository ClothesRepository;

// private  BasketRepository basketRepository;

// private ClientRepository clientRepository;

//     public ClothesController(ClothesRepository clothesRepository, BasketRepository basketRepository,ClientRepository clientRepository) {

//         this.ClothesRepository = clothesRepository;

//         this.basketRepository = basketRepository;

//         this.clientRepository = clientRepository;
        
//     }

//     @PostMapping("/basket/add/{clothesId}/{clientId}")

//     public ResponseEntity<String> addToBasket(@PathVariable UUID clothesId, @PathVariable UUID clientId) {

//         Clothes clothes = ClothesRepository.get(clothesId);

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

//     @DeleteMapping("/basket/remove/{clothesId}")

//     public ResponseEntity<String> removeFromBasket(@PathVariable UUID clothesId) {

//         basketRepository.removeFromBasket(clothesId);

//         return ResponseEntity.ok("Item removed from basket.");

//     }

//     @GetMapping("/basket")

//     public ResponseEntity<Basket> getBasket() {

//         Basket basket = basketRepository.getBasket();

//         if (basket == null || basket.clothesIds.isEmpty()) {

//             return ResponseEntity.ok(new Basket());

//         }

//         return ResponseEntity.ok(basket);
//     }

//     @PostMapping("/order/{clientId}")

// public ResponseEntity<String> order(@PathVariable UUID clientId) {

//     Basket basket = basketRepository.getBasket();

//     Client client = clientRepository.get(clientId);

//     if (basket == null || basket.clothesIds.isEmpty()) {

//         return ResponseEntity.badRequest().body("Basket is empty. Please add items to the basket before ordering.");

//     }

//     if (client == null) {

//         return ResponseEntity.badRequest().body("Client not found.");

//     }

//     // Calculate total price of items in the basket
//     int totalPrice = 0;

//     StringBuilder unavailableItems = new StringBuilder();

//     for (UUID clothesId : basket.clothesIds) {

//         Clothes clothes = ClothesRepository.get(clothesId);

//         if (clothes == null) {

//             unavailableItems.append("Item with ID ").append(clothesId).append(" is out of stock.\n");

//         } else {

//             totalPrice += clothes.price;

//         }
//     }

//     // Check for unavailable items

//     if (unavailableItems.length() > 0) {

//         return ResponseEntity.badRequest().body(unavailableItems.toString());

//     }

//     // Check if the client has enough credits

//     if (client.credit < totalPrice) {

//         return ResponseEntity.badRequest()

//                 .body("Insufficient credits. Your total is " + totalPrice + ", but you only have " + client.credit + " credits.");

//     }

//     // Deduct the total price from client's credits

//     client.credit -= totalPrice;

//     // Update client data in the repository

//     clientRepository.update(client); 

//     // Clear the basket (assuming the order is successfully placed)

//     basketRepository.clearBasket();

//     return ResponseEntity.ok("Order placed successfully! Remaining credits: " + client.credit);
// }

//     @GetMapping("/clothes")

//     public List<Clothes> list(){

//         return ClothesRepository.list();

//     }

//     @GetMapping("/clothes/{id}")

//     public Clothes get(@PathVariable("id") UUID id){

//         return ClothesRepository.get(id);

//     }

//     @PostMapping("/clothes")

//     public Clothes add(@RequestBody Clothes p){

//         ClothesRepository.add(p);

//         return p;

//     }

//     @PutMapping("/clothes/{id}")

//     public Clothes update(@PathVariable("id") UUID id, @RequestBody Clothes p){

//         p.id = id;

//         ClothesRepository.update(p);

//         return p;

//     }

//     @DeleteMapping("/clothes/{id}")

//     public void delete(@PathVariable("id") UUID id){

//         ClothesRepository.remove(id);

//     }

//  }
