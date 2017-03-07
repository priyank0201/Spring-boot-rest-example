package com.priyank.example.controller;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.priyank.example.domain.Booking;
import com.priyank.example.exception.EntityNotFoundException;
import com.priyank.example.service.BookingService;


@RestController
@RequestMapping(value = "/example/hotel/lochsideHouse")
public class HotelBookingController {

    @Autowired
    private BookingService bookingService;
    
    
    @RequestMapping(value = "/example/hotel/lochsideHouse/create/",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createBooking(@Valid @RequestBody Booking booking,
                                 HttpServletRequest request, HttpServletResponse response) throws EntityNotFoundException {
    	Booking bookingCreated = this.bookingService.createBooking(booking);
        response.setHeader("Location", request.getRequestURL().append("/").append(bookingCreated.getBookingId()).toString());
    }
    
    @RequestMapping(value = "/example/hotel/lochsideHouse/bookings/room/{id}",
            method = RequestMethod.GET,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Set<Booking> getBookingsForARoom(@RequestParam(value = "roomId", required = true) Long roomId,
                                 HttpServletRequest request, HttpServletResponse response) throws EntityNotFoundException {
    	return this.bookingService.retrieveRoomBookings(roomId);
    }
    
    @RequestMapping(value = "/example/hotel/lochsideHouse/bookings/customer/{id}",
            method = RequestMethod.GET,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public Set<Booking> getBookingsForACustomer(@RequestParam(value = "customerId", required = true) Long customerId,
                                 HttpServletRequest request, HttpServletResponse response) throws EntityNotFoundException {
    	return this.bookingService.retrieveCustomerBookings(customerId);
    }
    
    @RequestMapping(value = "/example/hotel/lochsideHouse/available/room/{id}",
            method = RequestMethod.GET,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public boolean checkAvailabilityForARoom(@RequestParam(value = "roomId", required = true) Long roomId,
    												@RequestParam(value = "checkInDate", required = true) Date checkInDate,
    													@RequestParam(value = "checkOutDate", required = true) Date checkOutDate,
                                 HttpServletRequest request, HttpServletResponse response) throws EntityNotFoundException {
    	return this.bookingService.checkAvailabilityForARoomOnGivenDates(roomId, checkInDate, checkOutDate);
    }
}
