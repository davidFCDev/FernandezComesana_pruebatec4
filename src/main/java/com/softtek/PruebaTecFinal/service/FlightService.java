package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.FlightDto;
import com.softtek.PruebaTecFinal.model.Flight;
import com.softtek.PruebaTecFinal.repository.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService implements IFlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    private FlightDto convertToDto(Flight flight) {
        return modelMapper.map(flight, FlightDto.class);
    }

    @Override
    public List<Flight> listFlights() {
        List<Flight> flights = flightRepository.findAll();
        if (flights.isEmpty()) {
            throw new RuntimeException("No flights found");
        }
        return flights;
    }

    @Override
    public List<FlightDto> listFlightsWithParams(LocalDate dateFrom, LocalDate dateTo, String origin, String destination) {
        List<Flight> flights;
        if (dateFrom != null && dateTo != null && origin != null && destination != null) {
            flights = findFlightsByDateRangeAndDestinationAndOrigin(dateFrom, dateTo, origin, destination);
        } else {
            flights = flightRepository.findAll();
        }
        return flights.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Método alternativo para filtrado por fecha y origen/destino
    private List<Flight> findFlightsByDateRangeAndDestinationAndOrigin(LocalDate dateFrom, LocalDate dateTo, String origin, String destination) {
        return flightRepository.findByDepartureDateBetweenAndOriginAndDestination(dateFrom, dateTo, origin, destination);
    }

    @Override
    public Flight listFlightId(String code) {
        Flight flight = flightRepository.findById(code).orElse(null);
        if (flight == null) {
            throw new RuntimeException("Flight with number " + code + " not found.");
        }
        return flight;
    }

    @Override
    public Flight addFlight(Flight flight) {
        if (flightRepository.existsById(flight.getCode())) {
            throw new RuntimeException("Flight with number " + flight.getCode() + " already exists.");
        }
        return flightRepository.save(flight);
    }

    @Override
    public Flight editFlight(Flight flight) {
      Flight existingFlight = flightRepository.findById(flight.getCode()).orElse(null);
        if (existingFlight == null) {
            throw new RuntimeException("Flight with number " + flight.getCode() + " not found.");
        }
        return flightRepository.save(flight);
    }

    @Override
    public Flight deleteFlight(String code) {
        Flight flight = flightRepository.findById(code).orElse(null);

        if (flight == null) {
            throw new RuntimeException("No flight found with number " + code);
        }

        if (!flight.getFlightBookings().isEmpty()) {
            throw new RuntimeException("Flight with number " + code + " has reservations.");
        }

        flightRepository.delete(flight);
        return flight;
    }

}
