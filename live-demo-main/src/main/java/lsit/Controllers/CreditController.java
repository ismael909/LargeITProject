// package lsit.Controllers;

// import java.util.UUID;

// import org.springframework.http.ResponseEntity;
// import static org.springframework.http.ResponseEntity.badRequest;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import lsit.Models.Client;
// import lsit.Repositories.ClientRepository;

// @RestController
// @RequestMapping("/credits")
// public class CreditController {

//     private final ClientRepository clientRepository;

//     public CreditController(ClientRepository clientRepository) {

//         this.clientRepository = clientRepository;

//     }

//     @GetMapping("/{clientId}")

//     public ResponseEntity<Integer> getCredits(@PathVariable UUID clientId) {

//         Client client = clientRepository.get(clientId);

//         if (client == null) {

//             return badRequest().body(null);

//         }

//         return ResponseEntity.ok(client.credit);
//     }

//     @PostMapping("/{clientId}/deduct/{amount}")

//     public ResponseEntity<String> deductCredits(@PathVariable UUID clientId, @PathVariable int amount) {

//         Client client = clientRepository.get(clientId);

//         if (client == null) {

//             return ResponseEntity.badRequest().body("Client not found.");

//         }

//         if (client.credit < amount) {

//             return ResponseEntity.badRequest().body("Insufficient credits.");

//         }

//         client.credit -= amount;

//         clientRepository.update(client);

//         return ResponseEntity.ok("Credits deducted successfully. Remaining credits: " + client.credit);

//     }
// }

