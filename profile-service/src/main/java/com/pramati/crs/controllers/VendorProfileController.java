package com.pramati.crs.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pramati.crs.profiles.dto.VendorDTO;

/**
 * 
 * @author srinivas nalla
 * @version 1.0.0
 * 
 * Exposes the API for CRUD operations for Vendors
 */
@RestController
@RequestMapping("v1/vendors")
public class VendorProfileController extends AbstractDataConroller<VendorDTO, Long>{
}
