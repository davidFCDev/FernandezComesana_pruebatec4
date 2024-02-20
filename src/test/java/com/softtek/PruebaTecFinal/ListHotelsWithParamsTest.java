package com.softtek.PruebaTecFinal;

import com.softtek.PruebaTecFinal.dto.HotelDto;
import com.softtek.PruebaTecFinal.exception.NoHotelsFoundException;
import com.softtek.PruebaTecFinal.exception.NoRoomsAvailableException;
import com.softtek.PruebaTecFinal.model.Hotel;
import com.softtek.PruebaTecFinal.model.Room;
import com.softtek.PruebaTecFinal.model.RoomBooking;
import com.softtek.PruebaTecFinal.repository.HotelRepository;
import com.softtek.PruebaTecFinal.repository.RoomBookingRepository;
import com.softtek.PruebaTecFinal.service.HotelService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListHotelsWithParamsTest {

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomBookingRepository roomBookingRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void listHotelsWithParams_Successful() {

        String city = "City";
        LocalDate dateFrom = LocalDate.of(2024, 2, 1);
        LocalDate dateTo = LocalDate.of(2024, 2, 5);

        Hotel hotel = new Hotel();
        hotel.setName("Hotel 1");
        hotel.setCity(city);
        Room room = new Room();
        room.setCode(101L);
        room.setHotel(hotel);
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        hotel.setRooms(rooms);

        HotelDto hotelDto = new HotelDto();
        hotelDto.setName("Hotel 1");
        hotelDto.setCity(city);
        when(hotelRepository.findByCity(city)).thenReturn(List.of(hotel));
        when(modelMapper.map(hotel, HotelDto.class)).thenReturn(hotelDto);
        when(roomBookingRepository.findByRoomAndDateFromLessThanEqualAndDateToGreaterThanEqual(any(), any(), any()))
                .thenReturn(new ArrayList<>());

        List<HotelDto> result = hotelService.listHotelsWithParams(city, dateFrom, dateTo);

        assertEquals(1, result.size());
        assertEquals(hotelDto, result.get(0));
    }

    @Test
    public void listHotelsWithParams_NoHotelsFound() {

        String city = "City";

        when(hotelRepository.findByCity(city)).thenReturn(new ArrayList<>());

        assertThrows(NoHotelsFoundException.class, () -> {
            hotelService.listHotelsWithParams(city, null, null);
        });
    }

    @Test
    public void listHotelsWithParams_NoRoomsAvailable() {

        String city = "City";
        LocalDate dateFrom = LocalDate.of(2024, 2, 1);
        LocalDate dateTo = LocalDate.of(2024, 2, 5);

        Hotel hotel = new Hotel();
        hotel.setName("Hotel 1");
        hotel.setCity(city);
        Room room = new Room();
        room.setCode(101L);
        room.setHotel(hotel);
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        hotel.setRooms(rooms);

        when(hotelRepository.findByCity(city)).thenReturn(List.of(hotel));
        when(roomBookingRepository.findByRoomAndDateFromLessThanEqualAndDateToGreaterThanEqual(any(), any(), any()))
                .thenReturn(List.of(new RoomBooking()));

        assertThrows(NoRoomsAvailableException.class, () -> {
            hotelService.listHotelsWithParams(city, dateFrom, dateTo);
        });
    }
}
