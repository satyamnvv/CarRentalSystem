package com.pramati.crs.booking.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.crs.booking.dto.BookingDTO;
import com.pramati.crs.booking.dto.NewBookingDTO;
import com.pramati.crs.booking.service.BookingService;

/**
 * The service endpoints for bookings related operations
 * 
 * @author ashishr
 *
 */
@RestController
@RequestMapping("/v1/bookings")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class BookingController {

	private BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	/**
	 * Service endpoint to fetch all the bookings
	 * 
	 * @return List of bookings
	 */
	@GetMapping(produces = "application/hal+json")
	public Resources<BookingDTO> findAllBookings() {

		List<BookingDTO> allBookings = bookingService.findAllBookings();

		for (BookingDTO bookingDTO : allBookings) {
			Link selfLink = linkTo(methodOn(BookingController.class).getBookingDetailsById(bookingDTO.getBookingId()))
					.withSelfRel();
			bookingDTO.add(selfLink);
		}

		Link link = linkTo(methodOn(BookingController.class).findAllBookings()).withSelfRel();

		Resources<BookingDTO> bookings = new Resources<>(allBookings, link);
		return bookings;
	}

	/**
	 * Service endpoint to fetch a booking details y id
	 * 
	 * @param bookingId The booking id
	 * @return The booking details
	 */
	@GetMapping(value = "/{bookingId}", produces = "application/hal+json")
	public BookingDTO getBookingDetailsById(@PathVariable long bookingId) {

		BookingDTO booking = bookingService.getBookingDetailsById(bookingId);

		Link link = linkTo(methodOn(BookingController.class).getBookingDetailsById(bookingId)).withSelfRel();
		booking.add(link);

		return booking;
	}

	/**
	 * Service endpoint to fetch all the bookings for a user
	 * 
	 * @param userId The user id
	 * @return Lsit of bookings
	 */
	@GetMapping(value = "/users/{userId}", produces = "application/hal+json")
	public Resources<BookingDTO> getAllBookingsForUser(@PathVariable long userId) {

		List<BookingDTO> allBookings = bookingService.getAllBookingsForUser(userId);

		for (BookingDTO bookingDTO : allBookings) {
			Link selfLink = linkTo(methodOn(BookingController.class).getBookingDetailsById(bookingDTO.getBookingId()))
					.withSelfRel();
			bookingDTO.add(selfLink);
		}

		Link link = linkTo(methodOn(BookingController.class).getAllBookingsForUser(userId)).withSelfRel();

		Resources<BookingDTO> bookings = new Resources<>(allBookings, link);
		return bookings;
	}

	/**
	 * Service endpoint to create a new booking
	 * 
	 * @param newBookingDto new booking details
	 * @return Newly created booking details
	 */
	@PostMapping(produces = "application/hal+json")
	public BookingDTO createNewBooking(@RequestBody NewBookingDTO newBookingDto) {

		BookingDTO newBooking = bookingService.createNewBooking(newBookingDto);

		Link link = linkTo(methodOn(BookingController.class).createNewBooking(newBookingDto)).withSelfRel();
		newBooking.add(link);

		return newBooking;
	}

	/**
	 * Service endpoint to update a booking status
	 * 
	 * @param bookingId     The booking id
	 * @param bookingStatus The new status of booking
	 * @return
	 */
	@PatchMapping(value = "/{bookingId}", produces = "application/hal+json")
	public BookingDTO updateBooking(@PathVariable long bookingId, @RequestParam String bookingStatus) {

		BookingDTO updatedBooking = bookingService.updateBooking(bookingId, bookingStatus);

		Link link = linkTo(methodOn(BookingController.class).updateBooking(bookingId, bookingStatus)).withSelfRel();
		updatedBooking.add(link);

		return updatedBooking;
	}

}
