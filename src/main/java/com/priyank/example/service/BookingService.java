package com.priyank.example.service;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.priyank.example.dao.jpa.BookingRepository;
import com.priyank.example.dao.jpa.RoomRepository;
import com.priyank.example.domain.Booking;
import com.priyank.example.exception.EntityNotFoundException;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;
    
	public Set<Booking> retrieveRoomBookings(Long roomId)
			throws EntityNotFoundException {
		final Set<Booking> bookings = bookingRepository.findBookingsByRoomId(roomId);
		
		return bookings;
	}
    
	public Set<Booking> retrieveCustomerBookings(Long customerId)
			throws EntityNotFoundException {
		final Set<Booking> bookings = bookingRepository.findBookingsByCustomerId(customerId);
		
		return bookings;
	}

	public boolean checkAvailabilityForARoomOnGivenDates(Long roomId, 
			Date checkInDate, Date checkOutDate) throws EntityNotFoundException{
		final Booking booking = bookingRepository.findAvailabilityForARoomOnGivenDates(roomId,checkInDate, checkOutDate);
		
		return booking == null;
	}
	
	public Booking createBooking(Booking booking){

    	if (booking.getCheckInDate() != null && 
    			booking.getCheckOutDate() != null && booking.getCustomer().getId() !=null) {
            booking.setState(Booking.State.CREATED);
    	}
    	
    	return bookingRepository.save(booking);	
	}
}