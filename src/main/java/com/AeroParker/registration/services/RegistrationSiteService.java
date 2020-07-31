package com.AeroParker.registration.services;

import com.AeroParker.registration.models.RegistrationForm;
import com.AeroParker.registration.dao.RegistrationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RegistrationSiteService {
  private final RegistrationDao registrationDao;

  @Autowired
  RegistrationSiteService(RegistrationDao registrationDao) {
    this.registrationDao = registrationDao;
  }

  public void createRegistrationForSite(RegistrationForm registrationForm, Integer siteID) {
    Assert.notNull(registrationForm, "registrationForm must be provided");
    Assert.notNull(siteID, "siteID must be provided");

    Integer customerId = this.registrationDao.createCustomer(registrationForm);
    this.registrationDao.createCustomerSite(customerId, siteID);
  }
}
