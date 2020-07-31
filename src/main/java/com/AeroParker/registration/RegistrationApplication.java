package com.AeroParker.registration;

import com.AeroParker.registration.dao.RegistrationDao;
import org.jdbi.v3.core.Jdbi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@Configuration
public class RegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

	@Bean
	public javax.validation.Validator validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public Jdbi createJdbi() {
		final String hostname = "localhost";
		final String database = "AeroParker";
		final String user = "root";
		final String password = "password";
		final String connectionString = "jdbc:mysql://" + hostname + "/" + database + "?user=" + user + "&password=" + password + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";
		Jdbi jdbi = Jdbi.create(connectionString);

		return jdbi.installPlugins();
	}

	@Bean
	public RegistrationDao registrationDao(Jdbi jdbi) {
		return jdbi.onDemand(RegistrationDao.class);
	}
}
