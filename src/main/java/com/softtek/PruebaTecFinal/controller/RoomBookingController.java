package com.softtek.PruebaTecFinal.controller;

import com.softtek.PruebaTecFinal.exception.ReservationException;
import com.softtek.PruebaTecFinal.model.RoomBooking;
import com.softtek.PruebaTecFinal.service.IRoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agency")
public class RoomBookingController {

    @Autowired
    private IRoomBookingService roomBookingService;

    @PostMapping("/hotel-booking/new")
    public ResponseEntity<Object> newRoomBooking(@RequestBody RoomBooking roomBooking) {
        try {
            return ResponseEntity.ok(roomBookingService.addRoomBooking(roomBooking));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/hotel-booking/list")
    public ResponseEntity<Object> listRoomBookings() {
        try {
            return ResponseEntity.ok(roomBookingService.listRoomBookings());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/hotel-booking/{id}")
    public ResponseEntity<Object> listRoomBookingId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(roomBookingService.listRoomBookingId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/hotel-booking/edit/{id}")
    public ResponseEntity<Object> editRoomBooking(@PathVariable Long id, @RequestBody RoomBooking roomBooking) {
        try {
            roomBookingService.editRoomBooking(id, roomBooking);
            return ResponseEntity.ok("Room booking edited successfully.");
        } catch (RuntimeException | ReservationException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/hotel-booking/delete/{id}")
    public ResponseEntity<Object> deleteRoomBooking(@PathVariable Long id) {
        try {
            roomBookingService.deleteRoomBooking(id);
            return ResponseEntity.ok("Room booking deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
