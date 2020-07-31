package com.AeroParker.registration.controllers;

import com.AeroParker.registration.models.database.Site;
import com.AeroParker.registration.models.RegistrationForm;
import com.AeroParker.registration.services.RegistrationSiteService;
import com.AeroParker.registration.services.SiteService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

  private final SiteService siteService;
  private final RegistrationSiteService registrationSiteService;

  @Autowired
  public RegistrationController(SiteService siteService, RegistrationSiteService registrationSiteService) {
    this.siteService = siteService;
    this.registrationSiteService = registrationSiteService;
  }


  // Serving http://localhost:port/?site=avaloncity - default
  // Serving http://localhost:port/?site=aceparks
  @GetMapping("/")
  public ModelAndView showRegistrationPage(@RequestParam(name="site", required = false) String siteNameParam,
      Model model,
      HttpSession session) {
    Map<String, Object> modelMap = new HashMap<>();

    if (!model.containsAttribute("registrationForm")) {
      modelMap.put("registrationForm", RegistrationForm.builder().build());
    }


    final String siteName = siteNameParam != null ? siteNameParam : "avaloncity";

    List<Site> sites = siteService.getSites();
      Optional<Site> matchedSite = sites.stream()
          .filter(
              siteInDb -> siteInDb.getName().replace(" ", "").equalsIgnoreCase(siteName))
          .findFirst();

    if (!matchedSite.isPresent()) {
      return new ModelAndView("error");
    }

    session.setAttribute("siteID", matchedSite.get().getId());
    modelMap.put("siteName", matchedSite.get().getName());

    ModelAndView modelAndView = new ModelAndView("registration", modelMap);

    return modelAndView;
  }

  @PostMapping("/")
  public String submitRegistration(@Valid @ModelAttribute RegistrationForm registrationForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      HttpSession session) {

    if (!registrationForm.isEmailValid(registrationForm.getEmail())) {
      bindingResult.rejectValue("email", "registrationForm.email", "please enter a valid email address");
    }

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("registrationForm", registrationForm);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationForm", bindingResult);
      return "redirect:/";
    }

    Integer siteId = (Integer) session.getAttribute("siteID");
    registrationForm.setEmail(registrationForm.getEmail().toLowerCase());
    // create registration in DB
    registrationSiteService.createRegistrationForSite(registrationForm, siteId);

    return "redirect:/success";
  }

}
