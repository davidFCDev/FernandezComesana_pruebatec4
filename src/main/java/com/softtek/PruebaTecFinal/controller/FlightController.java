package com.softtek.PruebaTecFinal.controller;

import com.softtek.PruebaTecFinal.dto.FlightDto;
import com.softtek.PruebaTecFinal.model.Flight;
import com.softtek.PruebaTecFinal.service.IFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class FlightController {

    @Autowired
    private IFlightService flightService;

    @PostMapping("/flights/new")
    public ResponseEntity<String> newFlight(@RequestBody Flight flight){
        try {
            flightService.addFlight(flight);
            return ResponseEntity.ok("New flight added successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/flights")
    public ResponseEntity<?> listFlights(
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
            @RequestParam(name = "origin", required = false) String origin,
            @RequestParam(name = "destination", required = false) String destination) {

        // Si no hay filtros, devuelve todos los vuelos disponibles
        List<FlightDto> flights = flightService.listFlightsWithParams(dateFrom, dateTo, origin, destination);

        if (flights.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No flights found with the specified filters");
        } else {
            return ResponseEntity.ok(flights);
        }
    }

    @GetMapping("/flights/{code}")
    public Flight listFlightId(@PathVariable String code){
        return flightService.listFlightId(code);
    }

    @PutMapping("/flights/edit/{code}")
    public ResponseEntity<String> editFlight(@RequestBody Flight flight, @PathVariable String code) {
        try {
            Flight editedFlight = flightService.editFlight(flight);
            return ResponseEntity.ok("Flight edited successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/flights/delete/{code}")
    public ResponseEntity<String> deleteFlight(@PathVariable String code) {
        try {
            Flight flight = flightService.deleteFlight(code);
            return ResponseEntity.ok("Flight deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
