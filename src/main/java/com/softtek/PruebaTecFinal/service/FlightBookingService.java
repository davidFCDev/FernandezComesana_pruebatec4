package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.FlightBookingDto;
import com.softtek.PruebaTecFinal.exception.ReservationException;
import com.softtek.PruebaTecFinal.model.Client;
import com.softtek.PruebaTecFinal.model.Flight;
import com.softtek.PruebaTecFinal.model.FlightBooking;
import com.softtek.PruebaTecFinal.repository.FlightBookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightBookingService implements IFlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private IFlightService flightService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    private FlightBookingDto convertToDTO(FlightBooking flightBooking) {
        return modelMapper.map(flightBooking, FlightBookingDto.class);
    }

    @Override
    public List<FlightBookingDto> listFlightBookings() {
        List<FlightBooking> flightBookings = flightBookingRepository.findAll();
        if (flightBookings.isEmpty()) {
            throw new RuntimeException("No flight bookings found.");
        }
        return flightBookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FlightBookingDto listFlightBookingId(Long id) {
        FlightBooking flightBooking = flightBookingRepository.findById(id).orElse(null);
        if (flightBooking == null) {
            throw new RuntimeException("Flight booking not found for id: " + id);
        }
        return convertToDTO(flightBooking);
    }

    @Override
    public String addFlightBooking(FlightBooking flightBooking) throws ReservationException {
        // Validacion necesaria antes de crear la reserva
        Flight validatedFlight = validateFlight(flightBooking);

        Double totalPrice = calculateTotalPrice(validatedFlight, flightBooking);
        updateAvailableSeats(validatedFlight, flightBooking.getClients().size());
        flightBookingRepository.save(flightBooking);
        return "Flight reserved successfully. Total price: " + totalPrice;
    }

    // Método para validar el vuelo y todas las condiciones de reserva
    private Flight validateFlight(FlightBooking flightBooking) throws ReservationException {
        String flightCode = flightBooking.getFlight().getCode();

        // Verificar si el vuelo existe
        Flight flight = flightService.listFlightId(flightCode);
        if (flight == null ){
            throw new ReservationException("Flight not found.");
        }

        // Verificar si todos los clientes en la reserva existen
        for (Client client : flightBooking.getClients()) {
            Client existingClient = clientService.listClientDNI(client.getDni());
            if (existingClient == null) {
                throw new ReservationException("Client " + client.getDni() + " not found.");
            }
        }

        // Verificar si hay suficientes asientos disponibles
        if (flight.getTotalSeats() < flightBooking.getClients().size()) {
            throw new ReservationException("Not enough seats available.");
        }

        // Verificar si ya existe una reserva con el mismo vuelo y el mismo DNI
        for (Client client : flightBooking.getClients()) {
            List<FlightBooking> existingBookings = flightBookingRepository.findByFlightCodeAndClientsDni(flightCode, client.getDni());
            if (!existingBookings.isEmpty()) {
                throw new ReservationException("Client " + client.getDni() + " already has a reservation for this flight.");
            }
        }

        return flight;
    }


    // Método para calcular el precio total de la reserva
    private Double calculateTotalPrice(Flight flight, FlightBooking flightBooking) {
        Double pricePerSeat = flight.getPricePerSeat();
        Integer totalSeats = flightBooking.getClients().size();
        return pricePerSeat * totalSeats;
    }

    // Método para actualizar los asientos disponibles tras la reserva
    private void updateAvailableSeats(Flight flight, int reservedSeats) {
        flight.setTotalSeats(flight.getTotalSeats() - reservedSeats);
        flightService.editFlight(flight);
    }

    @Override
    public ResponseEntity<Object> deleteFlightBooking(Long id) {
        FlightBooking booking = flightBookingRepository.findById(id).orElse(null);

        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight booking not found for id: " + id);
        }

        try {
            flightBookingRepository.delete(booking);
            return ResponseEntity.ok("Flight booking deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting flight booking");
        }
    }
}
