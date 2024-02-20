package com.softtek.PruebaTecFinal.repository;

import com.softtek.PruebaTecFinal.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    List<Flight> findByDepartureDateBetweenAndOriginAndDestination(LocalDate date_from, LocalDate date_to, String origin, String destination);
}
