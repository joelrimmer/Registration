package com.AeroParker.registration.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class SuccessController {

  @GetMapping("/success")
  public String showRegistrationPage(SessionStatus sessionStatus) {
    sessionStatus.setComplete();
    return "successPage";
  }
}
