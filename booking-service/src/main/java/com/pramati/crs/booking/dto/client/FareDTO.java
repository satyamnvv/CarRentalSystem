package com.pramati.crs.booking.dto.client;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

public class FareDTO extends ResourceSupport {

	private long fareId;

	private double fare;

	private Date effeciveFrom;

	private Date effectiveUpto;

	private String carCategory;

	private String carType;

	private long vendorId;

	private boolean isActive;

	public long getFareId() {
		return fareId;
	}

	public void setFareId(long fareId) {
		this.fareId = fareId;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public Date getEffeciveFrom() {
		return effeciveFrom;
	}

	public void setEffeciveFrom(Date effeciveFrom) {
		this.effeciveFrom = effeciveFrom;
	}

	public Date getEffectiveUpto() {
		return effectiveUpto;
	}

	public void setEffectiveUpto(Date effectiveUpto) {
		this.effectiveUpto = effectiveUpto;
	}

	public String getCarCategory() {
		return carCategory;
	}

	public void setCarCategory(String carCategory) {
		this.carCategory = carCategory;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
