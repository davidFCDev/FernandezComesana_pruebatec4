package com.softtek.PruebaTecFinal.service;

import com.softtek.PruebaTecFinal.dto.HotelDto;
import com.softtek.PruebaTecFinal.exception.NoHotelsFoundException;
import com.softtek.PruebaTecFinal.exception.NoRoomsAvailableException;
import com.softtek.PruebaTecFinal.model.Hotel;
import com.softtek.PruebaTecFinal.model.Room;
import com.softtek.PruebaTecFinal.model.RoomBooking;
import com.softtek.PruebaTecFinal.repository.HotelRepository;
import com.softtek.PruebaTecFinal.repository.RoomBookingRepository;
import com.softtek.PruebaTecFinal.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService implements IHotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    private HotelDto convertToDTO(Hotel hotel) {
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto listHotelId(String code) {
        Hotel hotel = hotelRepository.findById(code).orElse(null);
        if (hotel == null) {
            throw new RuntimeException("Hotel not found for code: " + code);
        }
        return convertToDTO(hotel);
    }

    @Override
    public List<Hotel> listHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        if (hotels.isEmpty()) {
            throw new NoHotelsFoundException("No hotels found");
        }
        return hotels;
    }


    @Override
    public List<HotelDto> listHotelsWithParams(String city, LocalDate dateFrom, LocalDate dateTo) {
        List<Hotel> hotels;

        if (city != null) {
            hotels = hotelRepository.findByCity(city);
        } else {
            hotels = hotelRepository.findAll();
        }

        // Verificar si no se encuentran hoteles por la ciudad de búsqueda
        if (hotels.isEmpty()) {
            throw new NoHotelsFoundException("Hotels not found for city: " + city);
        }

        // Comprobación de disponibilidad de habitaciones en el rango de fechas proporcionado
        if (dateFrom != null && dateTo != null) {
            LocalDate currentDate = dateFrom;
            while (!currentDate.isAfter(dateTo)) {
                LocalDate date = currentDate;
                hotels.forEach(hotel -> hotel.getRooms().forEach(room -> {
                    List<RoomBooking> bookings = roomBookingRepository.findByRoomAndDateFromLessThanEqualAndDateToGreaterThanEqual(room, date, date);
                    room.setBookings(bookings);
                }));
                currentDate = currentDate.plusDays(1);
            }

            hotels.forEach(hotel -> hotel.setRooms(
                    hotel.getRooms().stream()
                            .filter(room -> room.getBookings().isEmpty())
                            .collect(Collectors.toList())
            ));

            // Verificar si no hay habitaciones disponibles en el rango de fechas proporcionado
            if (hotels.stream().allMatch(hotel -> hotel.getRooms().isEmpty())) {
                throw new NoRoomsAvailableException("No rooms available for the selected dates.");
            }
        }

        return hotels.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void addHotel(Hotel hotel) throws Exception {
        validateHotel(hotel);
        String hotelCode = hotel.getCode();
        if (hotelCode != null && hotelRepository.existsById(hotelCode)) {
            throw new Exception("Hotel with code " + hotelCode + " already exists.");
        }
        hotelRepository.save(hotel);
    }

    private void validateHotel(Hotel hotel) throws Exception {
        if (hotel == null) {
            throw new Exception("Hotel is required.");
        }
        if (hotel.getName() == null || hotel.getName().isEmpty()) {
            throw new Exception("Name is required.");
        }
        if (hotel.getCity() == null || hotel.getCity().isEmpty()) {
            throw new Exception("City is required.");
        }
    }

    @Override
    public Hotel editHotel(Hotel hotel) throws Exception {
        Hotel existingHotel = hotelRepository.findById(hotel.getCode()).orElse(null);
        validateHotel(existingHotel);
        return hotelRepository.save(hotel);
    }

    @Override
    public ResponseEntity<Object> deleteHotel(String code) {
        Hotel hotel = hotelRepository.findById(code).orElse(null);

        if (hotel == null) {
            return ResponseEntity.status(404).body("Hotel not found for code: " + code);
        }

        // Verificar si alguna habitación tiene asignado un RoomBooking
        for (Room room : hotel.getRooms()) {
            if (!room.getBookings().isEmpty()) {
                return ResponseEntity.status(400).body("Cannot delete hotel because it contains rooms with bookings");
            }
        }

        try {
            hotelRepository.delete(hotel);
            return ResponseEntity.ok("Hotel deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting hotel");
        }
    }

}
