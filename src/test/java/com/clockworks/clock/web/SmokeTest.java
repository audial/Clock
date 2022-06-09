package com.clockworks.clock.web;

import com.clockworks.clock.controller.SubscriptionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

	@Autowired
	private SubscriptionController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}
}