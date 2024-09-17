package com.pramati.crs.fare.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.crs.fare.dto.FareDTO;
import com.pramati.crs.fare.dto.NewFareDTO;
import com.pramati.crs.fare.service.FareService;

/**
 * Controller class for Fare related services
 * 
 * @author ashishr
 *
 */
@RestController
@RequestMapping("/v1/fares")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class FareController {

	private FareService fareService;

	public FareController(FareService fareService) {
		this.fareService = fareService;
	}

	/**
	 * Service endpoint to get the active fare for a particular car for a vendor
	 * 
	 * @param carCategory The car category
	 * @param carType     The car type
	 * @param vendorId    The vendor id
	 * @return Fare details
	 */
	@GetMapping(produces = "application/hal+json")
	public FareDTO getActiveFare(@RequestParam String carCategory, @RequestParam String carType,
			@RequestParam long vendorId) {

		// Get the fare details
		FareDTO activeFare = fareService.getActiveFare(carCategory, carType, vendorId);

		// Generate HATEOAS self link
		Link link = linkTo(methodOn(FareController.class).getActiveFare(carCategory, carType, vendorId)).withSelfRel();
		activeFare.add(link);

		return activeFare;
	}

	/**
	 * Service endpoint to add new fare details for a vendor and car
	 * 
	 * @param newFareDto The new fare details
	 * @return Newly created fare details
	 */
	@PostMapping(produces = "application/hal+json")
	public FareDTO addNewFare(@RequestBody NewFareDTO newFareDto) {

		// Create new fare
		FareDTO newFare = fareService.addNewFare(newFareDto);

		// Generate HATEOAS self link
		Link link = linkTo(methodOn(FareController.class).addNewFare(newFareDto)).withSelfRel();
		newFare.add(link);

		return newFare;
	}

	/**
	 * Service endpoint to get all the fares for a vendor
	 * 
	 * @param vendorId The vendor id
	 * @return List of fares
	 */
	@GetMapping(value = "/vendors/{vendorId}", produces = "application/hal+json")
	public Resources<FareDTO> getAllFaresForVendor(@PathVariable long vendorId) {

		// Get all the fares for the vendor
		List<FareDTO> vendorFares = fareService.getAllFaresForVendor(vendorId);

		// Generate self links for all individual records
		for (FareDTO fareDTO : vendorFares) {
			Link selfLink = linkTo(methodOn(FareController.class).getActiveFare(fareDTO.getCarCategory(),
					fareDTO.getCarType(), fareDTO.getVendorId())).withSelfRel();
			fareDTO.add(selfLink);
		}

		// Generate HATEOAS self link
		Link link = linkTo(methodOn(FareController.class).getAllFaresForVendor(vendorId)).withSelfRel();
		Resources<FareDTO> fares = new Resources<>(vendorFares, link);

		return fares;
	}
}