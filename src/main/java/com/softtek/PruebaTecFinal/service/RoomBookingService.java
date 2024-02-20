package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.RoomBookingDto;
import com.softtek.PruebaTecFinal.exception.ReservationException;
import com.softtek.PruebaTecFinal.model.Room;
import com.softtek.PruebaTecFinal.model.RoomBooking;
import com.softtek.PruebaTecFinal.repository.RoomBookingRepository;
import com.softtek.PruebaTecFinal.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomBookingService implements IRoomBookingService {

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;

    private RoomBookingDto convertToDTO(RoomBooking roomBooking) {
        return modelMapper.map(roomBooking, RoomBookingDto.class);
    }

    @Override
    public List<RoomBookingDto> listRoomBookings() {
        List<RoomBooking> roomBookings = roomBookingRepository.findAll();
        if (roomBookings.isEmpty()) {
            throw new RuntimeException("No room bookings found.");
        }
        return roomBookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomBookingDto listRoomBookingId(Long id) {
        RoomBooking roomBooking = roomBookingRepository.findById(id).orElse(null);
        if (roomBooking == null) {
            throw new RuntimeException("Room booking not found for id: " + id);
        }
        return convertToDTO(roomBooking);
    }

    @Override
    public String addRoomBooking(RoomBooking roomBooking) throws ReservationException {
        Long roomCode = roomBooking.getRoom().getCode();

        Room room = roomRepository.findById(roomCode).orElse(null);
        if (room == null) {
            throw new ReservationException("Room not found for code: " + roomCode);
        }

        // Obtener todas las reservas existentes para la misma habitación
        List<RoomBooking> existingBookings = roomBookingRepository.findByRoom(room);

        // Verificar si hay superposición de fechas
        for (RoomBooking existingBooking : existingBookings) {
            if (areDatesOverlapping(roomBooking.getDateFrom(), roomBooking.getDateTo(),
                    existingBooking.getDateFrom(), existingBooking.getDateTo())) {
                throw new ReservationException("Las fechas seleccionadas para la reserva ya están ocupadas.");
            }
        }

        Double totalPrice = calculateTotalPrice(room, roomBooking);
        roomBookingRepository.save(roomBooking);
        return "Room reserved successfully. Total price: " + totalPrice;
    }

    // Método para verificar si dos rangos de fechas se superponen
    private boolean areDatesOverlapping(LocalDate startDate1, LocalDate endDate1,
                                        LocalDate startDate2, LocalDate endDate2) {
        return !endDate1.isBefore(startDate2) && !startDate1.isAfter(endDate2);
    }



    private Double calculateTotalPrice(Room room, RoomBooking roomBooking) {
        Double price = room.getPricePerNight();
        LocalDate checkIn = roomBooking.getDateFrom();
        LocalDate checkOut = roomBooking.getDateTo();
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        return price * days;
    }

    @Override
    public void deleteRoomBooking(Long id) {
        RoomBooking roomBooking = roomBookingRepository.findById(id).orElse(null);
        if (roomBooking == null) {
            throw new RuntimeException("Room booking not found for id: " + id);
        }
        roomBookingRepository.delete(roomBooking);
    }

    @Override
    public void editRoomBooking(Long id, RoomBooking roomBooking) throws ReservationException {
        RoomBooking existingRoomBooking = roomBookingRepository.findById(id).orElse(null);
        if (existingRoomBooking == null) {
            throw new RuntimeException("Room booking not found for id: " + id);
        }
        roomBooking.setId(id);
        roomBookingRepository.save(roomBooking);
    }

}

