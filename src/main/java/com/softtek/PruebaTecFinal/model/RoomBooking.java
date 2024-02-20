package com.softtek.PruebaTecFinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    @ManyToOne
    @JoinColumn(name = "room_code", referencedColumnName = "code")
    private Room room;

    @ManyToMany
    @JoinTable(
            name = "room_reserve_client",
            joinColumns = @JoinColumn(name = "room_reserve_id"),
            inverseJoinColumns = @JoinColumn(name = "client_dni")
    )
    private List<Client> clients;

}
