package com.pramati.crs.booking.service;

import java.util.List;

import com.pramati.crs.booking.dto.BookingDTO;
import com.pramati.crs.booking.dto.NewBookingDTO;

/**
 * Service interface for booking related operations
 * 
 * @author ashishr
 *
 */
public interface BookingService {

	/**
	 * Service method to fetch all the available bookings
	 * 
	 * @return List of Bookings
	 */
	public List<BookingDTO> findAllBookings();

	/**
	 * Service method to fetch a bookings details by id
	 * 
	 * @param bookingId The booking id
	 * @return The booking details
	 */
	public BookingDTO getBookingDetailsById(long bookingId);

	/**
	 * Service methods to fetch all the bookings made by user
	 * 
	 * @param userId The user id
	 * @return List of bookings
	 */
	public List<BookingDTO> getAllBookingsForUser(long userId);

	/**
	 * Service method to create a new bookings
	 * 
	 * @param newBookingDto New booking details
	 * @return Newly created booking
	 */
	public BookingDTO createNewBooking(NewBookingDTO newBookingDto);

	/**
	 * Service mthod to update a booking status
	 * 
	 * @param bookingId     The booking id
	 * @param bookingStatus The new status
	 * @return The updated booking details
	 */
	public BookingDTO updateBooking(long bookingId, String bookingStatus);
}
