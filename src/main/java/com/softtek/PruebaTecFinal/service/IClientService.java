package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.model.Client;

import java.util.List;

public interface IClientService {

    public List<Client> listClients();
    public Client listClientDNI(String dni);
    public Client addClient(Client client);
    public Client editClient(Client client);
    public void deleteClient(String dni);
}
