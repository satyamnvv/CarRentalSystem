package com.pramati.crs.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pramati.crs.booking.entity.Booking;

/**
 * Repository interface for booking related operations
 * 
 * @author ashishr
 *
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	/**
	 * Repository method to fetch list of bookings by user id
	 * 
	 * @param userId The user id
	 * @return List of bookings
	 */
	public List<Booking> findByUserId(long userId);
}
