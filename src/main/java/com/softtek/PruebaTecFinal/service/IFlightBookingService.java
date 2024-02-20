package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.FlightBookingDto;
import com.softtek.PruebaTecFinal.exception.ReservationException;
import com.softtek.PruebaTecFinal.model.FlightBooking;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFlightBookingService {

    public List<FlightBookingDto> listFlightBookings();
    public FlightBookingDto listFlightBookingId(Long id);
    public String addFlightBooking(FlightBooking flightBooking) throws ReservationException;
    public ResponseEntity<Object> deleteFlightBooking(Long id);
}
