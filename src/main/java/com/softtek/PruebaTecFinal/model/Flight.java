package com.softtek.PruebaTecFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {

    @Id
    private String code;

    private String origin;
    private String destination;
    private String seatType;
    private Double pricePerSeat;
    private Integer totalSeats;
    private LocalDate departureDate;

    @JsonIgnore
    @OneToMany(mappedBy = "flight")
    private List<FlightBooking> flightBookings;
}
