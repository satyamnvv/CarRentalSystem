package com.pramati.crs.fare;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ForwardedHeaderFilter;

@SpringBootApplication
@EnableDiscoveryClient
public class FareServiceApplication {

	public static void main(String[] args) {
		String[] extededArgs = Arrays.copyOf(args, args.length+1);
		extededArgs[args.length] = "-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector";
		SpringApplication.run(FareServiceApplication.class, extededArgs);
	}


	@Bean
	FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
		FilterRegistrationBean<ForwardedHeaderFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new ForwardedHeaderFilter());
		return bean;
	}
}
