package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.FlightDto;
import com.softtek.PruebaTecFinal.model.Flight;

import java.time.LocalDate;
import java.util.List;

public interface IFlightService {

    public List<Flight> listFlights();
    public List<FlightDto> listFlightsWithParams(LocalDate dateFrom, LocalDate dateTo, String origin, String destination);
    public Flight listFlightId(String code);
    public Flight addFlight(Flight flight);
    public Flight editFlight(Flight flight);
    public Flight deleteFlight(String code);
}
