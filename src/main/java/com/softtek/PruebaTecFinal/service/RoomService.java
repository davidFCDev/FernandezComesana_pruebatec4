package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.RoomDto;
import com.softtek.PruebaTecFinal.model.Hotel;
import com.softtek.PruebaTecFinal.model.Room;
import com.softtek.PruebaTecFinal.repository.HotelRepository;
import com.softtek.PruebaTecFinal.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;

    private RoomDto convertToDTO(Room room) {
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> listRoomsByHotel(String code) {
        Hotel hotel = hotelRepository.findById(code).orElse(null);
        if (hotel == null) {
            throw new RuntimeException("Hotel not found for code: " + code);
        }
        List<RoomDto> roomDtos = hotel.getRooms().stream().map(this::convertToDTO).collect(Collectors.toList());
        return roomDtos;
    }

    @Override
    public RoomDto listRoomId(Long code) {
        Room room = roomRepository.findById(code).orElse(null);
        if (room == null) {
            throw new RuntimeException("Room not found for number: " + code);
        }
        return convertToDTO(room);
    }

    @Override
    public Room addRoom(Room room, String code) {
        Hotel hotel = hotelRepository.findById(code).orElse(null);
        if (hotel == null) {
            throw new RuntimeException("Hotel not found for code: " + code);
        }
        room.setHotel(hotel);
        return roomRepository.save(room);
    }

    @Override
    public void editRoom(Room room) {
        if (roomRepository.findById(room.getCode()).isPresent()) {
            roomRepository.save(room);
        } else {
            throw new RuntimeException("Room not found for number: " + room.getCode());
        }
    }

    @Override
    public void deleteRoom(Long code) {
        if (roomRepository.findById(code).isPresent()) {
            roomRepository.deleteById(code);
        } else {
            throw new RuntimeException("Room not found for number: " + code);
        }
    }
}
