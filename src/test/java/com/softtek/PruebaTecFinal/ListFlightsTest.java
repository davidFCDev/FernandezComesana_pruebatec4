package com.softtek.PruebaTecFinal;

import com.softtek.PruebaTecFinal.model.Flight;
import com.softtek.PruebaTecFinal.repository.FlightRepository;
import com.softtek.PruebaTecFinal.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListFlightsTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Test
    public void listFlightsTest() {
        List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight();
        flight.setCode("1");
        flight.setOrigin("Origen de prueba");
        flight.setDestination("Destino de prueba");
        flight.setTotalSeats(100);
        flight.setPricePerSeat(100.0);
        flight.setDepartureDate(java.time.LocalDate.now());
        flights.add(flight);
        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> result = flightService.listFlights();
        assertEquals(1, result.size());
    }

    @Test
    public void listFlightsEmptyTest() {
        when(flightRepository.findAll()).thenReturn(new ArrayList<>());

        try {
            flightService.listFlights();
        } catch (RuntimeException e) {
            assertEquals("No flights found", e.getMessage());
        }
    }

}
