package lsit.Controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lsit.Models.Client;
import lsit.Repositories.GCSClientRepository;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final GCSClientRepository clientRepository;

    public ClientController(GCSClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/{clientId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'SALES', 'OWNER')") // Define roles for accessing client info
    public ResponseEntity<Client> getClient(@PathVariable UUID clientId) {
        Client client = clientRepository.get(clientId);

        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(client);
    }
}
