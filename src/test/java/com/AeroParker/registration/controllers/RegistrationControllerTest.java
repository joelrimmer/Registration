package com.AeroParker.registration.controllers;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.AeroParker.registration.models.database.Site;
import com.AeroParker.registration.services.RegistrationSiteService;
import com.AeroParker.registration.services.SiteService;
import java.util.Arrays;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RegistrationControllerTest {

  private final MockMvc mockMvc;
  private static final String REGISTRATION_VIEW = "registration";
  private static final String REGISTRATION_ENDPOINT = "/";
  private static final String ERROR_VIEW = "error";
  private static final String SUCCESS_ENDPOINT = "/success";

  private final SiteService siteServiceMock;
  private final RegistrationSiteService registrationSiteServiceMock;

  public RegistrationControllerTest() {
    siteServiceMock = Mockito.mock(SiteService.class);
    registrationSiteServiceMock = Mockito.mock(RegistrationSiteService.class);
    RegistrationController registrationController = new RegistrationController(siteServiceMock,
        registrationSiteServiceMock);
    this.mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
  }

  @Test
  public void registrationDefaultPageShows() throws Exception {

    Site site = Site.builder().id(1).name("avaloncity").build();

    when(siteServiceMock.getSites()).thenReturn(Arrays.asList(site));

    HttpSession httpSession = mockMvc.perform(get(REGISTRATION_ENDPOINT))
        .andExpect(status().isOk())
        .andExpect(view().name(REGISTRATION_VIEW))
        .andExpect(model().attributeExists("siteName"))
        .andReturn().getRequest().getSession();

    assertThat(httpSession.getAttribute("siteID")).isEqualTo(1);
  }

  @Test
  public void registrationAceParksPageShows() throws Exception {

    Site site1 = Site.builder().id(1).name("avaloncity").build();
    Site site2 = Site.builder().id(2).name("aceparks").build();

    when(siteServiceMock.getSites()).thenReturn(Arrays.asList(site1, site2));

    HttpSession httpSession = mockMvc.perform(get(REGISTRATION_ENDPOINT + "?site=aceparks"))
        .andExpect(status().isOk())
        .andExpect(view().name(REGISTRATION_VIEW))
        .andExpect(model().attributeExists("siteName"))
        .andReturn().getRequest().getSession();

    assertThat(httpSession.getAttribute("siteID")).isEqualTo(2);
  }

  @Test
  public void registrationErrorPageShows() throws Exception {

    Site site = Site.builder().id(1).name("avaloncity").build();

    when(siteServiceMock.getSites()).thenReturn(Arrays.asList(site));

    mockMvc.perform(get(REGISTRATION_ENDPOINT + "?site=abc"))
        .andExpect(status().isOk())
        .andExpect(view().name(ERROR_VIEW));
  }

  @Test
  public void registrationSubmits() throws Exception {

    mockMvc.perform(post(REGISTRATION_ENDPOINT).param("email", "a@abc.com")
        .param("title", "mr")
        .param("firstName", "joel")
        .param("lastName", "rimmer")
        .param("address1", "abc")
        .param("address2", "abc")
        .param("city", "man")
        .param("postcode", "m1 1gh")
        .param("telephone", "11 111").sessionAttr("siteID", 1))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(SUCCESS_ENDPOINT));

    verify(registrationSiteServiceMock, times(1)).createRegistrationForSite(ArgumentMatchers.any(), eq(1));

  }

  @Test
  public void emptyFormSubmit_thenThrowError() throws Exception {

    mockMvc.perform(post(REGISTRATION_ENDPOINT)
        .sessionAttr("siteID", 1))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"))
    .andExpect(flash().attribute("org.springframework.validation.BindingResult.registrationForm", hasProperty("fieldErrors", hasSize(7))));

  }

  @Test
  public void invalidEmailSubmit_thenThrowError() throws Exception {

    mockMvc.perform(post(REGISTRATION_ENDPOINT).param("email", "a@ab@c@....com")
        .param("title", "mr")
        .param("firstName", "joel")
        .param("lastName", "rimmer")
        .param("address1", "abc")
        .param("address2", "abc")
        .param("city", "man")
        .param("postcode", "m1 1gh")
        .param("telephone", "11 111").sessionAttr("siteID", 1))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"))
        .andExpect(flash().attribute("org.springframework.validation.BindingResult.registrationForm", hasProperty("fieldErrors", hasSize(1))))
        .andExpect(flash().attribute("registrationForm", notNullValue()));

  }
}
