package com.softtek.PruebaTecFinal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingDto {

    private String dateFrom;
    private String dateTo;
    private HotelRoomDto room;
    private List<ClientDto> clients;
}
