package com.pramati.crs.fare.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pramati.crs.fare.dto.FareDTO;
import com.pramati.crs.fare.dto.NewFareDTO;
import com.pramati.crs.fare.exception.APIException;
import com.pramati.crs.fare.service.FareService;
import com.pramati.crs.fare.utils.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FareControllerTest {

	private MockMvc mockMvc;

	@Mock
	private FareService fareServiceMock;

	@InjectMocks
	private FareController fareControllerUnderTest;

	@Before
	public void setUp() throws Exception {

		Mockito.reset(fareServiceMock);

		mockMvc = MockMvcBuilders.standaloneSetup(fareControllerUnderTest).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getActiveFare_ShouldReturnActiveFareDetails() throws Exception {

		FareDTO fareDetails = new FareDTO(1, 100.0, new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), "CAR", "SEDAN", 1, true);

		when(fareServiceMock.getActiveFare("CAR", "SEDAN", 1)).thenReturn(fareDetails);

		mockMvc.perform(get("/v1/fares").param("carCategory", "CAR").param("careType", "SEDAN").param("vendorId",
				String.valueOf(1))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].fareId", is(1))).andExpect(jsonPath("$[0].carCategory", is("CAR")))
				.andExpect(jsonPath("$[0].carType", is("SEDAN"))).andExpect(jsonPath("$[0].fare", is(100.0)))
				.andExpect(jsonPath("$[0].vendorId", is(1)));

		verify(fareServiceMock, times(1)).getActiveFare("CAR", "SEDAN", 1);
		verifyNoMoreInteractions(fareServiceMock);
	}

	@Test(expected = APIException.class)
	public void getActiveFare_ShouldThrowNotFoundException() throws Exception {

		when(fareServiceMock.getActiveFare("CAR", "SEDAN", 1)).thenReturn(null);

		mockMvc.perform(get("/v1/fares").param("carCategory", "CAR").param("careType", "SEDAN").param("vendorId",
				String.valueOf(1))).andExpect(status().is(404));

		verify(fareServiceMock, times(1)).getActiveFare("CAR", "SEDAN", 1);
		verifyNoMoreInteractions(fareServiceMock);
	}

	@Test
	public void addNewFare_ShouldReturnNewlyCreatedFareDetails() throws Exception {

		FareDTO fareToAdd = new FareDTO(1, 100.0, new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), "CAR", "SEDAN", 1, true);

		FareDTO createdFare = new FareDTO(1, 100.0, new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), "CAR", "SEDAN", 1, true);

		when(fareServiceMock.addNewFare(any(NewFareDTO.class))).thenReturn(createdFare);

		mockMvc.perform(post("/v1/fares").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(TestUtils.convertObjectToJsonBytes(fareToAdd))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].fareId", is(1))).andExpect(jsonPath("$[0].carCategory", is("CAR")))
				.andExpect(jsonPath("$[0].carType", is("SEDAN"))).andExpect(jsonPath("$[0].fare", is(100.0)))
				.andExpect(jsonPath("$[0].vendorId", is(1)));

		ArgumentCaptor<NewFareDTO> dtoCaptor = ArgumentCaptor.forClass(NewFareDTO.class);
		verify(fareServiceMock, times(1)).addNewFare(dtoCaptor.capture());
		verifyNoMoreInteractions(fareServiceMock);

		NewFareDTO dtoArgument = dtoCaptor.getValue();
		assertThat(dtoArgument.getVendorId(), is(1));
		assertThat(dtoArgument.getPricePerDay(), is(100.0));

	}

	public void getAllFaresForVendor_ShouldReturnListOfFares() throws Exception {

		FareDTO fare1 = new FareDTO(1, 100.0, new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), "CAR", "SEDAN", 1, true);

		FareDTO fare2 = new FareDTO(2, 150.0, new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), "CAR", "SUV", 1, true);

		when(fareServiceMock.getAllFaresForVendor(1)).thenReturn(Arrays.asList(fare1, fare2));

		mockMvc.perform(get("/v1/fares")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].fareId", is(1))).andExpect(jsonPath("$[0].carCategory", is("CAR")))
				.andExpect(jsonPath("$[0].carType", is("SEDAN"))).andExpect(jsonPath("$[0].fare", is(100.0)))
				.andExpect(jsonPath("$[0].vendorId", is(1)))

				.andExpect(jsonPath("$[0].fareId", is(2))).andExpect(jsonPath("$[0].carCategory", is("CAR")))
				.andExpect(jsonPath("$[0].carType", is("SUV"))).andExpect(jsonPath("$[0].fare", is(150.0)))
				.andExpect(jsonPath("$[0].vendorId", is(1)));

		verify(fareServiceMock, times(1)).getAllFaresForVendor(any(Integer.class));
		verifyNoMoreInteractions(fareServiceMock);

	}

}
