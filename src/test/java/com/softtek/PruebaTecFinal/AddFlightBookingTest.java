package com.softtek.PruebaTecFinal;

import com.softtek.PruebaTecFinal.exception.ReservationException;
import com.softtek.PruebaTecFinal.model.Client;
import com.softtek.PruebaTecFinal.model.Flight;
import com.softtek.PruebaTecFinal.model.FlightBooking;
import com.softtek.PruebaTecFinal.repository.FlightBookingRepository;
import com.softtek.PruebaTecFinal.service.ClientService;
import com.softtek.PruebaTecFinal.service.FlightBookingService;
import com.softtek.PruebaTecFinal.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddFlightBookingTest {

    @Mock
    private FlightService flightService;

    @Mock
    private ClientService clientService;

    @Mock
    private FlightBookingRepository flightBookingRepository;

    @InjectMocks
    private FlightBookingService flightBookingService;

    @Test
    public void testAddFlightBooking_Successful() throws ReservationException {
        MockitoAnnotations.openMocks(this);

        Flight flight = new Flight();
        flight.setCode("FL123");
        flight.setTotalSeats(100);
        flight.setPricePerSeat(100.0);

        List<Client> clients = new ArrayList<>();
        Client client1 = new Client();
        client1.setDni("12345678A");
        clients.add(client1);

        FlightBooking flightBooking = new FlightBooking();
        flightBooking.setFlight(flight);
        flightBooking.setClients(clients);

        when(flightService.listFlightId("FL123")).thenReturn(flight);
        when(clientService.listClientDNI("12345678A")).thenReturn(client1);

        String result = flightBookingService.addFlightBooking(flightBooking);

        assertEquals("Flight reserved successfully. Total price: 100.0", result);
    }

    @Test
    public void testAddFlightBooking_Unsuccessful() throws ReservationException {

        MockitoAnnotations.openMocks(this);

        Flight flight = new Flight();
        flight.setCode("FL123");
        flight.setTotalSeats(100);
        flight.setPricePerSeat(100.0);

        List<Client> clients = new ArrayList<>();
        Client client1 = new Client();
        client1.setDni("12345678A");
        clients.add(client1);

        FlightBooking flightBooking = new FlightBooking();
        flightBooking.setFlight(flight);
        flightBooking.setClients(clients);


        when(flightService.listFlightId("FL123")).thenReturn(flight);
        when(clientService.listClientDNI("12345678A")).thenReturn(null);

        assertThrows(ReservationException.class, () -> {
            flightBookingService.addFlightBooking(flightBooking);
        });
    }

}
