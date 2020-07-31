package com.AeroParker.registration.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SuccessControllerTest {

  private final MockMvc mockMvc;
  private static final String SUCCESS_ENDPOINT = "/success";
  private static final String SUCCESS_VIEW = "successPage";


  public SuccessControllerTest() {
    SuccessController successController = new SuccessController();
    this.mockMvc = MockMvcBuilders.standaloneSetup(successController).build();
  }

  @Test
  public void successPageShows() throws Exception {

    mockMvc.perform(get(SUCCESS_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(view().name(SUCCESS_VIEW));

  }


}
