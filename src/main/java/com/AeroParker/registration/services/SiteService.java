package com.AeroParker.registration.services;

import com.AeroParker.registration.models.database.Site;
import java.util.List;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteService {

  private final Jdbi jdbi;

  @Autowired
  public SiteService(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public List<Site> getSites() {
    try (Handle handle = jdbi.open()) {
      return handle
          .createQuery("(SELECT * FROM Sites)")
          .mapToBean(Site.class)
          .list();
    }
  }

}
