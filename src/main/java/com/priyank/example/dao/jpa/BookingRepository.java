package com.priyank.example.dao.jpa;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.priyank.example.domain.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {
	
	Set<Booking> findBookingsByRoomId(Long id);
	
	Set<Booking> findBookingsByCustomerId(Long id);
	
	@Query("SELECT b FROM Booking b where b.room.id = :roomId and ( (b.checkInDate > :checkInDate and b.checkInDate > :checkOutDate)  or "
    		+ "(b.checkOutDate <= :checkInDate and  b.checkOutDate < :checkOutDate) )")
	Booking findAvailabilityForARoomOnGivenDates(Long roomId, Date checkInDate, Date checkOutDate);
}
