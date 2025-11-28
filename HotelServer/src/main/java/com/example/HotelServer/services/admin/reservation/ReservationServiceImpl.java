package com.example.HotelServer.services.admin.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.HotelServer.dto.ReservationResponseDto;
import com.example.HotelServer.entity.Reservation;
import com.example.HotelServer.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    public static final int SEARCH_RESULT_PER_PAGE = 4 ;

    public ReservationResponseDto getAllReservations(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto).toList());
        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());
        return reservationResponseDto;
    }

}
