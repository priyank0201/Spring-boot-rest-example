package com.priyank.example.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Customer")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Customer {

	@Id
	private Long id;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<Booking> bookings = new HashSet<Booking>();

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
