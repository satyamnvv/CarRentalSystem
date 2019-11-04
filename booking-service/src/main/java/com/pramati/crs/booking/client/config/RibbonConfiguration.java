package com.pramati.crs.booking.client.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;

@RibbonClient(name = "fare-service")
public class RibbonConfiguration {

	@Bean
	public IRule loadBlancingRule() {

//		new WeightedResponseTimeRule();
//		new AvailabilityFilteringRule();
//		new WeightedResponseTimeRule();
		return new RoundRobinRule();
	}
}
