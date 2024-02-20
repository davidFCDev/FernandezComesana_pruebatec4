package com.softtek.PruebaTecFinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {

    @Id
    private String dni;

    private String name;
    private String lastName;
    private String email;

    @JsonIgnore
    @ManyToMany(mappedBy = "clients")
    private List<FlightBooking> flightBookings;

    @JsonIgnore
    @ManyToMany(mappedBy = "clients")
    private List<RoomBooking> roomBookings;
}
