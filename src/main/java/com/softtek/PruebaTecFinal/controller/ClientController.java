package com.softtek.PruebaTecFinal.controller;

import com.softtek.PruebaTecFinal.model.Client;
import com.softtek.PruebaTecFinal.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping("/new")
    public ResponseEntity<String> newClient(@RequestBody Client client) {
        try {
            clientService.addClient(client);
            return ResponseEntity.ok("New client added successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add new client: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listClients() {
        try {
            return ResponseEntity.ok(clientService.listClients());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to list clients: " + e.getMessage());
        }
    }

    @GetMapping("/{dni}")
    public ResponseEntity<?> listClientDNI(@PathVariable String dni) {
        try {
            return ResponseEntity.ok(clientService.listClientDNI(dni));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to list client: " + e.getMessage());
        }
    }

    @PutMapping("/edit/{dni}")
    public ResponseEntity<String> editClient(@PathVariable String dni, @RequestBody Client client) {
        try {
            client.setDni(dni);
            clientService.editClient(client);
            return ResponseEntity.ok("Client edited successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to edit client: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{dni}")
    public ResponseEntity<String> deleteClient(@PathVariable String dni) {
        try {
            clientService.deleteClient(dni);
            return ResponseEntity.ok("Client deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete client: " + e.getMessage());
        }
    }

}
