package com.fywss.spring.userservice.domain.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserValidationTest {

	private static ValidatorFactory validatorFactory;
	private static Validator validator;

	@BeforeClass
	public static void createValidator() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@AfterClass
	public static void close() {
		validatorFactory.close();
	}

	@Test
	public void createUserShouldHaveNoViolations() {
		User u = new User("steve", "kay", "sk@fywss.com", "Good4password");
		Set<ConstraintViolation<User>> violations = validator.validate(u);
		assertTrue(violations.isEmpty());
	}

	@Test
	public void createUserShouldDetectInvalidFirstName() {
		User u = new User("s", "kay", "sk@fywss.com", "Good4password");
		Set<ConstraintViolation<User>> violations = validator.validate(u);
		assertEquals(violations.size(), 1);

		ConstraintViolation<User> violation = violations.iterator().next();
		assertEquals("firstName must be between 2 and 25 characters long", violation.getMessage());
		assertEquals("firstName", violation.getPropertyPath().toString());
		assertEquals("s", violation.getInvalidValue());
	}
	
	@Test
	public void createUserShouldDetectInvalidEmail() {
		User u = new User("stan", "kay", "bad", "Good4password");
		Set<ConstraintViolation<User>> violations = validator.validate(u);
		assertEquals(violations.size(), 1);

		ConstraintViolation<User> violation = violations.iterator().next();
		assertEquals("Email should be valid", violation.getMessage());
		assertEquals("email", violation.getPropertyPath().toString());
		assertEquals("bad", violation.getInvalidValue());
	}
	
	@Test
	public void createUserShouldDetectInvalidPassword() {
		User u = new User("stu", "kay", "sk@fywss.com", "notverygood");
		Set<ConstraintViolation<User>> violations = validator.validate(u);
		assertEquals(violations.size(), 1);

		ConstraintViolation<User> violation = violations.iterator().next();
		assertEquals("Password must have one number, one lowercase and one uppercase character.", violation.getMessage());
		assertEquals("secret", violation.getPropertyPath().toString());
		assertEquals("notverygood", violation.getInvalidValue());
	}
	
	@Test
	public void createUserShouldDetectMultipleProblems() {
		User u = new User("s", "kay", "oops", "short");
		Set<ConstraintViolation<User>> violations = validator.validate(u);
		assertEquals(violations.size(), 4);
	}
}
