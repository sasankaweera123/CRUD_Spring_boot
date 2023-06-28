package com.sasanka.ManagementSystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class ManagementSystemApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void corsConfigurer() {
		ManagementSystemApplication msa = new ManagementSystemApplication();
		msa.corsConfigurer();
	}


}
