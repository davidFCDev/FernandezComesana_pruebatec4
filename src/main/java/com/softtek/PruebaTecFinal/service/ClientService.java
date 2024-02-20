package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.model.Client;
import com.softtek.PruebaTecFinal.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> listClients() {
        if (clientRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("No clients found!");
        }
        return clientRepository.findAll();
    }

    @Override
    public Client listClientDNI(String dni) {
        if (clientRepository.findById(dni).isEmpty()) {
            throw new IllegalArgumentException("Client with provided DNI not found!");
        }
        return clientRepository.findById(dni).orElse(null);
    }

    @Override
    public Client addClient(Client client) {
        if (clientRepository.findById(client.getDni()).isPresent()) {
            throw new IllegalArgumentException("Client with provided DNI already exists!");
        }
        return clientRepository.save(client);
    }

    @Override
    public Client editClient(Client client) {
        if (clientRepository.findById(client.getDni()).isEmpty()) {
            throw new IllegalArgumentException("Client with provided DNI not found!");
        }
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(String dni) {
        Client client = clientRepository.findById(dni).orElse(null);
        if (client == null) {
            throw new IllegalArgumentException("Client with provided DNI not found!");
        }
        if (!client.getFlightBookings().isEmpty() || !client.getRoomBookings().isEmpty()) {
            throw new IllegalArgumentException("Client has active bookings!");
        }
        clientRepository.deleteById(dni);
    }
}
