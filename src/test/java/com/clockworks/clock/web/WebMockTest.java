package com.clockworks.clock.web;

import com.clockworks.clock.controller.SubscriptionController;
import com.clockworks.clock.model.RegisterResponse;
import com.clockworks.clock.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriptionController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService subscriptionService;

    @Test
    public void shouldRegisterUrl_andReturnRegistrationResponseAndCreatedStatus() throws Exception {
        when(subscriptionService.register("localhost", 5)).thenReturn(new RegisterResponse("localhost", 5));
        this.mockMvc
                .perform(post("/register?url=localhost&frequency=5")).andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString("{\"url\":\"localhost\",\"frequency\":5}")));
    }

    @Test
    public void shouldDeregisterUrl_andReturnOkStatus() throws Exception {
        doNothing().when(subscriptionService).deregister("localhost");
        this.mockMvc
                .perform(delete("/deregister?url=localhost")).andExpect(status().isOk());
    }

    @Test
    public void shouldAdjustFrequency_andReturnOkStatus() throws Exception {
        doNothing().when(subscriptionService).adjustFrequency("localhost", 5);
        this.mockMvc
                .perform(put("/frequency?url=localhost&frequency=5")).andExpect(status().isOk());
    }
}
