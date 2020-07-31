package com.AeroParker.registration.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {

  @NotBlank(message = "Field cannot be left empty")
  private String email;

  @NotBlank(message = "Field cannot be left empty")
  @Size(max=5, message = "Cannot be longer than 5 characters")
  private String title;

  @NotBlank(message = "Field cannot be left empty")
  @Size(max=50, message = "Cannot be longer than 50 characters")
  private String firstName;

  @NotBlank(message = "Field cannot be left empty")
  @Size(max=50, message = "Cannot be longer than 50 characters")
  private String lastName;

  @NotBlank(message = "Field cannot be left empty")
  @Size(max=255, message = "Cannot be longer than 255 characters")
  private String address1;

  @Size(max=255, message = "Cannot be longer than 255 characters")
  private String address2;

  @Size(max=255, message = "Cannot be longer than 255 characters")
  private String city;

  @NotBlank(message = "Field cannot be left empty")
  @Size(max=10, message = "Cannot be longer than 10 characters")
  private String postcode;

  @Size(max=20, message = "Cannot be longer than 20 characters")
  private String telephone;

  public static boolean isEmailValid(String email) {
    EmailValidator validator = EmailValidator.getInstance();
    return validator.isValid(email);
  }

}
