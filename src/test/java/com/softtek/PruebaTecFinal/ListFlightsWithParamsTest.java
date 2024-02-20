package com.softtek.PruebaTecFinal;

import com.softtek.PruebaTecFinal.dto.FlightDto;
import com.softtek.PruebaTecFinal.model.Flight;
import com.softtek.PruebaTecFinal.repository.FlightRepository;
import com.softtek.PruebaTecFinal.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListFlightsWithParamsTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void listFlightsWithParamsTest() {
        LocalDate dateFrom = LocalDate.of(2024, 2, 1);
        LocalDate dateTo = LocalDate.of(2024, 2, 20);
        String origin = "Origen de prueba";
        String destination = "Destino de prueba";

        List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight();
        flight.setCode("1");
        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setTotalSeats(100);
        flight.setPricePerSeat(100.0);
        flight.setDepartureDate(LocalDate.of(2024, 2, 10)); // Departure date within range
        flights.add(flight);

        when(flightRepository.findByDepartureDateBetweenAndOriginAndDestination(dateFrom, dateTo, origin, destination))
                .thenReturn(flights);

        when(modelMapper.map(flight, FlightDto.class)).thenReturn(new FlightDto());

        List<FlightDto> result = flightService.listFlightsWithParams(dateFrom, dateTo, origin, destination);
        assertEquals(1, result.size());
    }

    @Test
    public void listFlightsWithParamsEmptyTest() {
        LocalDate dateFrom = LocalDate.of(2024, 2, 1);
        LocalDate dateTo = LocalDate.of(2024, 2, 20);
        String origin = "Origen de prueba";
        String destination = "Destino de prueba";

        when(flightRepository.findByDepartureDateBetweenAndOriginAndDestination(dateFrom, dateTo, origin, destination))
                .thenReturn(new ArrayList<>());

        List<FlightDto> result = flightService.listFlightsWithParams(dateFrom, dateTo, origin, destination);
        assertEquals(0, result.size());
    }

}
