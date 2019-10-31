package com.pramati.crs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @author manikanth
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
public class AuthServiceApplicationTests {

	@Test
	public void contextLoads() {
		AuthServiceApplication.main(new String[] {});
	}

}
