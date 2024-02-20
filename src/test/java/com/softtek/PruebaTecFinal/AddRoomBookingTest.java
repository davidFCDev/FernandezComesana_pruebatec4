package com.softtek.PruebaTecFinal;

import com.softtek.PruebaTecFinal.exception.ReservationException;
import com.softtek.PruebaTecFinal.model.Room;
import com.softtek.PruebaTecFinal.model.RoomBooking;
import com.softtek.PruebaTecFinal.repository.RoomBookingRepository;
import com.softtek.PruebaTecFinal.repository.RoomRepository;
import com.softtek.PruebaTecFinal.service.RoomBookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddRoomBookingTest {

    @InjectMocks
    private RoomBookingService roomBookingService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomBookingRepository roomBookingRepository;

    @Test
    public void addRoomBookingSuccessfulTest() throws ReservationException {

        Room room = new Room();
        room.setCode(1L);
        room.setPricePerNight(100.0);

        RoomBooking roomBooking = new RoomBooking();
        roomBooking.setRoom(room);
        roomBooking.setDateFrom(LocalDate.of(2024, 2, 1));
        roomBooking.setDateTo(LocalDate.of(2024, 2, 5));

        when(roomRepository.findById(1L)).thenReturn(java.util.Optional.of(room));
        when(roomBookingRepository.findByRoom(room)).thenReturn(new ArrayList<>());

        String result = roomBookingService.addRoomBooking(roomBooking);

        assertEquals("Room reserved successfully. Total price: 400.0", result);
        verify(roomBookingRepository, times(1)).save(roomBooking);
    }

    @Test
    public void addRoomBookingWithOverlappingDatesTest() {

        Room room = new Room();
        room.setCode(1L);

        RoomBooking existingBooking = new RoomBooking();
        existingBooking.setDateFrom(LocalDate.of(2024, 2, 3));
        existingBooking.setDateTo(LocalDate.of(2024, 2, 6));

        RoomBooking roomBooking = new RoomBooking();
        roomBooking.setRoom(room);
        roomBooking.setDateFrom(LocalDate.of(2024, 2, 1));
        roomBooking.setDateTo(LocalDate.of(2024, 2, 5));

        List<RoomBooking> existingBookings = new ArrayList<>();
        existingBookings.add(existingBooking);

        when(roomRepository.findById(1L)).thenReturn(java.util.Optional.of(room));
        when(roomBookingRepository.findByRoom(room)).thenReturn(existingBookings);

        assertThrows(ReservationException.class, () -> {
            roomBookingService.addRoomBooking(roomBooking);
        });

        verify(roomBookingRepository, never()).save(roomBooking);
    }
}
