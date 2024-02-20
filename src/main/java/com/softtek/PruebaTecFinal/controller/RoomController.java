package com.softtek.PruebaTecFinal.controller;

import com.softtek.PruebaTecFinal.dto.RoomDto;
import com.softtek.PruebaTecFinal.model.Room;
import com.softtek.PruebaTecFinal.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @PostMapping("/hotels/{code}/rooms/new")
    public ResponseEntity<Object> addRoom(@PathVariable String code, @RequestBody Room room) {
        try {
            roomService.addRoom(room, code);
            return ResponseEntity.status(HttpStatus.CREATED).body("Room added successfully to hotel with code " + code);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/hotels/{code}/rooms")
    public ResponseEntity<Object> listRoomsByHotel(@PathVariable String code) {
        try {
            return ResponseEntity.ok(roomService.listRoomsByHotel(code));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/hotels/{code}/rooms/{number}")
    public ResponseEntity<Object> listRoomId(@PathVariable Long number) {
        try {
            return ResponseEntity.ok(roomService.listRoomId(number));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/hotels/{code}/rooms/edit/{id}")
    public ResponseEntity<Object> editRoom(@PathVariable Long id, @RequestBody Room room) {
        try {
            room.setCode(id);
            roomService.editRoom(room);
            return ResponseEntity.ok("Room updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/hotels/{code}/rooms/delete/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.ok("Room deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
