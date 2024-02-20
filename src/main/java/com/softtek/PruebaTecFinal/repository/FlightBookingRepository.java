package com.softtek.PruebaTecFinal.repository;

import com.softtek.PruebaTecFinal.model.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    List<FlightBooking> findByFlightCodeAndClientsDni(String flightCode, String clientDni);
}
