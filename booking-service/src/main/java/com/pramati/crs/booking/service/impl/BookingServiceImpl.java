package com.pramati.crs.booking.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pramati.crs.booking.client.FareServiceClient;
import com.pramati.crs.booking.dto.BookingDTO;
import com.pramati.crs.booking.dto.NewBookingDTO;
import com.pramati.crs.booking.dto.client.FareDTO;

import com.pramati.crs.booking.entity.Booking;
import com.pramati.crs.booking.entity.Booking.BookingStatus;
import com.pramati.crs.booking.exception.APIException;
import com.pramati.crs.booking.repository.BookingRepository;
import com.pramati.crs.booking.service.BookingService;
import com.pramati.crs.booking.utils.BookingConverter;

/**
 * Service class for booking related operations
 * 
 * @author ashishr
 *
 */
@Service
public class BookingServiceImpl implements BookingService {

	private BookingRepository bookingRepository;
	private FareServiceClient fareServiceClient;

	public BookingServiceImpl(BookingRepository bookingRepository, FareServiceClient fareServiceClient) {
		this.bookingRepository = bookingRepository;
		this.fareServiceClient = fareServiceClient;
	}

	/**
	 * Service method to get all the available bookings
	 */
	@Override
	public List<BookingDTO> findAllBookings() {

		// Fetch all bookings
		List<Booking> allBookings = bookingRepository.findAll();

		// Map bookings to dto
		List<BookingDTO> allBookingDtos = allBookings.stream().map(BookingConverter::mapBookingToDTO)
				.collect(Collectors.toList());

		return allBookingDtos;
	}

	/**
	 * Service method to fetch booking details by id
	 */
	@Override
	public BookingDTO getBookingDetailsById(long bookingId) {

		// Fetc booking details
		Optional<Booking> booking = bookingRepository.findById(bookingId);

		if (booking.isPresent()) {
			return BookingConverter.mapBookingToDTO(booking.get());
		} else {
			throw new APIException("No booking found with the given booking id", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Service method to fetch all the bookings for a user
	 */
	@Override
	public List<BookingDTO> getAllBookingsForUser(long userId) {

		// Fetch all user bookings
		List<Booking> userBookings = bookingRepository.findByUserId(userId);

		// Map bookings to dto
		List<BookingDTO> allUserBookingDtos = userBookings.stream().map(BookingConverter::mapBookingToDTO)
				.collect(Collectors.toList());
		return allUserBookingDtos;
	}

	/**
	 * Service method to create a new booking
	 */
	@Override
	public BookingDTO createNewBooking(NewBookingDTO newBookingDto) {

		// TODO: Fetch car details from id using profile service

		// create new booking object
		Booking booking = new Booking();
		booking.setUserId(newBookingDto.getUserId());
		booking.setCarId(newBookingDto.getCarId());
		booking.setLocationId(newBookingDto.getLocationId());
		booking.setFromDate(newBookingDto.getFromDate());
		booking.setToDate(newBookingDto.getToDate());
		booking.setBookingStatus(BookingStatus.BOOKED);


		// Fetch fare details from fare service
		FareDTO farePerDay;
		try {
			farePerDay = fareServiceClient.getActiveFare("A", "B", 1);
		} catch (Exception e) {
			throw new APIException("Fare service is not available", HttpStatus.BAD_REQUEST);
		}

		// TODO: calculate the total fare

		booking.setEstimatedFare(
				TimeUnit.DAYS.convert((newBookingDto.getFromDate().getTime() - newBookingDto.getToDate().getTime()),
						TimeUnit.MILLISECONDS) * farePerDay.getFare());
		booking.setBookedOn(new Date(System.currentTimeMillis()));

		// Save the new Booking.
		// TODO: Check for concurrency issues.
		booking = bookingRepository.save(booking);

		// Map the new booking to dto and return
		return BookingConverter.mapBookingToDTO(booking);
	}

	/**
	 * Service method to update the status of a booking
	 */
	@Override
	public BookingDTO updateBooking(long bookingId, String bookingStatus) {

		// Fetch existing booking
		Optional<Booking> booking = bookingRepository.findById(bookingId);
		if (!booking.isPresent()) {
			throw new APIException("No booking available with given id", HttpStatus.NOT_FOUND);
		}

		Booking existingBooking = booking.get();

		// Update status
		existingBooking.setBookingStatus(BookingStatus.valueOf(bookingStatus));

		existingBooking = bookingRepository.save(existingBooking);

		return BookingConverter.mapBookingToDTO(existingBooking);
	}

}
