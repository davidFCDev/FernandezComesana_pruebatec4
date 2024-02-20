package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.RoomBookingDto;
import com.softtek.PruebaTecFinal.exception.ReservationException;
import com.softtek.PruebaTecFinal.model.RoomBooking;

import java.util.List;

public interface IRoomBookingService {

    public List<RoomBookingDto> listRoomBookings();
    public RoomBookingDto listRoomBookingId(Long id);
    public String addRoomBooking(RoomBooking roomBooking) throws ReservationException;
    public void deleteRoomBooking(Long id);
    public void editRoomBooking(Long id, RoomBooking roomBooking) throws ReservationException;
}
