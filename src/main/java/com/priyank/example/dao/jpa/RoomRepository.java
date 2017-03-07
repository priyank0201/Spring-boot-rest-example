package com.priyank.example.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.priyank.example.domain.Booking;

public interface RoomRepository extends CrudRepository<Booking, Long> {
	
	Booking findBookingByRoomId(Long id);
	
	Booking findBookingByCustomerId(Long id);

}
