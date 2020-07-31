package com.AeroParker.registration.models.database;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Site {

  private Integer id;
  private String name;

}
