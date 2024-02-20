package com.softtek.PruebaTecFinal.controller;

import com.softtek.PruebaTecFinal.dto.FlightBookingDto;
import com.softtek.PruebaTecFinal.exception.ReservationException;
import com.softtek.PruebaTecFinal.model.FlightBooking;
import com.softtek.PruebaTecFinal.service.IFlightBookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class FlightBookingController {

    @Autowired
    private IFlightBookingService flightBookingService;

    @PostMapping("/flight-booking/new")
    public ResponseEntity<String> newFlightReserve(@RequestBody FlightBooking flightBooking) {
        try {
            String message = flightBookingService.addFlightBooking(flightBooking);
            return ResponseEntity.ok().body(message);
        } catch (ReservationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/flight-booking/list")
    public ResponseEntity<Object> listFlightReserves() {
        try {
            List<FlightBookingDto> flightBookings = flightBookingService.listFlightBookings();
            return ResponseEntity.ok(flightBookings);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/flight-booking/{id}")
    public ResponseEntity<Object> listFlightReserveId(@PathVariable Long id) {
        try {
            FlightBookingDto flightBooking = flightBookingService.listFlightBookingId(id);
            return ResponseEntity.ok(flightBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/flight-booking/delete/{id}")
    public ResponseEntity<Object> deleteFlightBooking(@PathVariable Long id) {
        try {
            return flightBookingService.deleteFlightBooking(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
