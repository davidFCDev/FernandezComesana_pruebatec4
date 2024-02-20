package com.softtek.PruebaTecFinal.repository;

import com.softtek.PruebaTecFinal.model.Room;
import com.softtek.PruebaTecFinal.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
    List<RoomBooking> findByRoomAndDateFromLessThanEqualAndDateToGreaterThanEqual(
            Room room, LocalDate dateFrom, LocalDate dateTo);
    List<RoomBooking> findByRoom(Room room);
}

