package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.RoomDto;
import com.softtek.PruebaTecFinal.model.Room;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    public List<RoomDto> listRoomsByHotel(String code);
    public RoomDto listRoomId(Long code);
    public Room addRoom(Room room, String code);
    public void editRoom(Room room);
    public void deleteRoom(Long code);
}
