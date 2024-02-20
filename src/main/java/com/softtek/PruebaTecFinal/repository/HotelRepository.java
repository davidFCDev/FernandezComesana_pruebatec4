package com.softtek.PruebaTecFinal.repository;

import com.softtek.PruebaTecFinal.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
    List<Hotel> findByCity(String city);
}
