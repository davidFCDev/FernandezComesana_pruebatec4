package com.softtek.PruebaTecFinal.repository;

import com.softtek.PruebaTecFinal.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
