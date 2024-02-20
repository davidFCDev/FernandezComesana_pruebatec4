package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.HotelDto;
import com.softtek.PruebaTecFinal.model.Hotel;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IHotelService {

    List<Hotel> listHotels();

    public List<HotelDto> listHotelsWithParams(String city, LocalDate dateFrom, LocalDate dateTo);
    public HotelDto listHotelId(String code);
    public void addHotel(Hotel hotel) throws Exception;
    public Hotel editHotel(Hotel hotel) throws Exception;
    public ResponseEntity<Object> deleteHotel(String code);
}
