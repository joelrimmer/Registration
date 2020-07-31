package com.AeroParker.registration.services;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.AeroParker.registration.models.RegistrationForm;
import com.AeroParker.registration.dao.RegistrationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RegistrationSiteServiceTest {

  @Mock
  private RegistrationDao registrationDaoMock;

  private RegistrationSiteService registrationSiteService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
    registrationSiteService = new RegistrationSiteService(registrationDaoMock);
  }

  @Test
  public void createRegistrationForSite() {
    RegistrationForm registrationForm = RegistrationForm.builder()
        .email("a@abc.com")
        .title("Mr")
        .firstName("Joel")
        .lastName("Rimmer")
        .address1("5 The Lane")
        .address2("Abc")
        .city("Man")
        .postcode("m1 1gh")
        .telephone("111 1111").build();
    when(registrationDaoMock.createCustomer(any())).thenReturn(99);

    registrationSiteService.createRegistrationForSite(registrationForm, 1);

    Mockito.verify(registrationDaoMock, Mockito.times(1))
        .createCustomer(eq(registrationForm));
    Mockito.verify(registrationDaoMock, Mockito.times(1))
        .createCustomerSite(99, 1);
  }
}