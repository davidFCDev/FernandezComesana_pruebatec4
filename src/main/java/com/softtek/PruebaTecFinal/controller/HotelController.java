package com.softtek.PruebaTecFinal.controller;

import com.softtek.PruebaTecFinal.dto.HotelDto;
import com.softtek.PruebaTecFinal.exception.NoHotelsFoundException;
import com.softtek.PruebaTecFinal.exception.NoRoomsAvailableException;
import com.softtek.PruebaTecFinal.model.Hotel;
import com.softtek.PruebaTecFinal.repository.HotelRepository;
import com.softtek.PruebaTecFinal.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agency")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    @PostMapping("/hotels/new")
    public ResponseEntity<String> addHotel(@RequestBody Hotel hotel) {
        try {
            hotelService.addHotel(hotel);
            return ResponseEntity.ok("Hotel with code " + hotel.getCode() + " added.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/hotels")
    public ResponseEntity<?> listHotels(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        try {
            List<HotelDto> hotels = hotelService.listHotelsWithParams(city, dateFrom, dateTo);
            return ResponseEntity.ok(hotels);
        } catch (NoHotelsFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (NoRoomsAvailableException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/hotels/{code}")
    public ResponseEntity<Object> listHotelId(@PathVariable String code) {
        try {
            return ResponseEntity.ok(hotelService.listHotelId(code));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/hotels/edit/{code}")
    public ResponseEntity<Object> editHotel(@PathVariable String code, @RequestBody Hotel hotel) {
        // Verificar si el hotel con el código proporcionado existe en la base de datos
        Optional<Hotel> existingHotelOptional = hotelRepository.findById(code);
        if (existingHotelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel with code " + code + " not found.");
        }

        Hotel existingHotel = existingHotelOptional.get();

        try {
            Hotel hotelUpdated = hotelService.editHotel(hotel);
            return ResponseEntity.ok("Hotel with code " + hotelUpdated.getCode() + " updated.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




    @DeleteMapping("/hotels/delete/{code}")
    public ResponseEntity<Object> deleteHotel(@PathVariable String code) {
        try {
            return hotelService.deleteHotel(code);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
