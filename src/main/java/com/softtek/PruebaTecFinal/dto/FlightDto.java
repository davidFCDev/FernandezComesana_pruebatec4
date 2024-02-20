package com.softtek.PruebaTecFinal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    private String code;
    private String origin;
    private String destination;
    private String departureDate;
    private String seatType;
    private Double price;
}
