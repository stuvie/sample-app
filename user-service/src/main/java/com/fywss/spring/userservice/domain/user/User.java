package com.fywss.spring.userservice.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "firstName cannot be null")
	@Size(min = 2, max = 25, message = "firstName must be between {min} and {max} characters long")
	private String firstName;
	
	@NotNull(message = "lastName cannot be null")
	@Size(min = 2, max = 25, message = "firstName must be between {min} and {max} characters long")
	private String lastName;
	
	@Email(message = "Email should be valid") @NotBlank @Column(unique = true)
	private String email;
	
	@NotBlank @Size(min = 8, max = 50, message = "Password must be between {min} and {max} characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", 
    	message = "Password must have one number, one lowercase and one uppercase character.")
	private String secret;
	
	public User() {
	}

	public User(String firstName, String lastName, String email, String secret) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.secret = secret;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", secret=" + secret + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
