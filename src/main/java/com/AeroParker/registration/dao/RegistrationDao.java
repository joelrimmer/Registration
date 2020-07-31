package com.AeroParker.registration.dao;

import com.AeroParker.registration.models.RegistrationForm;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface RegistrationDao {

  @SqlUpdate("INSERT INTO Customers (Registered, Email, Title, FirstName, LastName, Address1, Address2, City, Postcode, Telephone)" +
      " VALUES (now(), :email, :title, :firstName, :lastName, :address1, :address2, :city, :postcode, :telephone)")
  @GetGeneratedKeys({"id"})
  Integer createCustomer(@BindBean RegistrationForm registrationForm);

  @SqlUpdate("INSERT INTO Customer_Sites (CustomerID, SiteID)" +
      "VALUES (:customerId, :siteId)")
  void createCustomerSite(Integer customerId, Integer siteId);
}
