package com.pramati.crs.booking.client.config;

import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;

public class FeignConfiguration {

	@Bean
	public Logger.Level configureLogLevel() {
		return Logger.Level.BASIC;
	}

	@Bean
	public Request.Options timeoutConfiguration() {

		return new Request.Options(5000, 30000);
	}

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {

		return new BasicAuthRequestInterceptor("user", "password");
	}

	@Bean
	public RequestInterceptor headerRequestInterceptor() {

		return new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate template) {
				template.header("testHeader", "testHeaderValue");

			}
		};
	}

	@Bean
	public Retryer retryer() {

		return new Retryer.Default(1000, 8000, 3);
	}
}
