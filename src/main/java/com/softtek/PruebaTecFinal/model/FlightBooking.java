package com.softtek.PruebaTecFinal.model;

import jakarta.persistence.*;
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
public class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reserve_date")
    private LocalDate reserveDate;

    @ManyToOne
    @JoinColumn(name = "flight_code")
    private Flight flight;

    @ManyToMany
    @JoinTable(
            name = "flight_reserve_client",
            joinColumns = @JoinColumn(name = "flight_reserve_id"),
            inverseJoinColumns = @JoinColumn(name = "client_dni")
    )
    private List<Client> clients;

    @PrePersist
    public void setDefaultReserveDate() {
        this.reserveDate = LocalDate.now();
    }
}
