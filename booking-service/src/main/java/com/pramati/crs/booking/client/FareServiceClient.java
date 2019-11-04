package com.pramati.crs.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pramati.crs.booking.client.config.FeignConfiguration;
import com.pramati.crs.booking.dto.client.FareDTO;

@FeignClient(name = "fare-service", configuration = FeignConfiguration.class)
public interface FareServiceClient {

	@GetMapping("/v1/fares")
	public FareDTO getActiveFare(@RequestParam("carCategory") String carCategory,
			@RequestParam("carType") String carType, @RequestParam("vendorId") long vendorId);

}
