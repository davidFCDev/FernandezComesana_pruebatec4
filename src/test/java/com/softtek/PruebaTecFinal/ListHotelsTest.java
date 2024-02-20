package com.softtek.PruebaTecFinal;


import com.softtek.PruebaTecFinal.exception.NoHotelsFoundException;
import com.softtek.PruebaTecFinal.model.Hotel;
import com.softtek.PruebaTecFinal.repository.HotelRepository;
import com.softtek.PruebaTecFinal.service.HotelService;
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
public class ListHotelsTest {

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Test
    public void listHotelsTest() {
        List<Hotel> hotels = new ArrayList<>();
        Hotel hotel = new Hotel();
        hotel.setCode("1");
        hotel.setName("Hotel de prueba");
        hotel.setCity("Ciudad de prueba");
        hotels.add(hotel);
        when(hotelRepository.findAll()).thenReturn(hotels);

        List<Hotel> result = hotelService.listHotels();
        assertEquals(1, result.size());
    }

    @Test
    public void listHotelsEmptyTest() {
        when(hotelRepository.findAll()).thenReturn(new ArrayList<>());

        try {
            hotelService.listHotels();
        } catch (NoHotelsFoundException e) {
            assertEquals("No hotels found", e.getMessage());
        }
    }
}
