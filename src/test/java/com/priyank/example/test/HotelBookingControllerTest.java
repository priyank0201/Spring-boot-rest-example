package com.priyank.example.test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.priyank.example.Application;
import com.priyank.example.controller.HotelBookingController;
import com.priyank.example.domain.Booking;
import com.priyank.example.domain.Customer;
import com.priyank.example.domain.Room;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
public class HotelBookingControllerTest {

    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/example/hotel/lochsideHouse/create/[0-9]+";

    @InjectMocks
    HotelBookingController controller;

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldCreate() throws Exception {
    	Booking booking = mockBooking();
        byte[] bookingJson = toJson(booking);

        //CREATE
        MvcResult result = mvc.perform(post("/example/hotel/lochsideHouse/create/")
                .content(bookingJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //RETRIEVE
        mvc.perform(post("/example/hotel/lochsideHouse/create/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)));


        //RETRIEVE should fail
        mvc.perform(get("/example/hotel/lochsideHouse/create/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldRetrieveRoomBookings() throws Exception {
    	Booking r1 = mockBooking();
        byte[] r1Json = toJson(r1);
        //CREATE
        MvcResult result = mvc.perform(get("/example/hotel/lochsideHouse")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        Booking r2 = mockBooking();
        r2.setBookingId(id);
        byte[] r2Json = toJson(r2);


    }


    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }


    private Booking mockBooking() {
        Booking booking = new Booking();
        booking.setBookingId(1L);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
			booking.setCheckInDate(inputFormat.parse("2017-04-01"));
		
			booking.setCheckOutDate(inputFormat.parse("2017-04-02"));
        } catch (ParseException e) {
			e.printStackTrace();
		}
        booking.setPrice(100L);
        booking.setState(Booking.State.CREATED);
        booking.setCustomer(mockCustomer());
        booking.setRoom(mockRoom());
        return booking;
    }
    
    private Customer mockCustomer() {
    	Customer customer = new Customer();
    	customer.setId(1L);
    	customer.setBookings(new HashSet<Booking>());
    	
    	return customer;
    }
    
    private Room mockRoom() {
    	Room room = new Room(Room.Type.SINGLE_ROOM,1);
    	room.setId(1L);
    	return room;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return new ResultMatcher() {
            public void match(MvcResult result) {
                Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
                assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
            }
        };
    }

}
