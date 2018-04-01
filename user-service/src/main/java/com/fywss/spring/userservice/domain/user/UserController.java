package com.fywss.spring.userservice.domain.user;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

@ControllerAdvice
@RestController
@RequestMapping ("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	@ApiOperation (value = "Get all Users", notes = "Returns a List of User records", response = User.class, responseContainer = "List")
	public List<User> findAll() {
		return (List<User>) service.findAll();
	}
	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Gets one User based on id", notes = "Returns a single User record", response = User.class)
	public Optional<User> findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@GetMapping(params = {"email"})
	@ApiOperation (value = "Gets one User based on email", notes = "Returns a single User record", response = User.class)
    public Optional<User> findWithEmail(@RequestParam(value="email") String email) {
    	return service.findByEmail(email);
    }
    
	@PostMapping
	@ApiOperation (value = "Create a new User", notes = "Creates and returns a single User record", response = User.class)
	public User create(@Valid @RequestBody User obj, BindingResult bindingResult) {
		return service.create(obj);
	}
	
	@PutMapping
	@ApiOperation (value = "Update an existing User", notes = "Updates and returns the modified User record", response = User.class)
	public User update(@Valid @RequestBody User obj, @PathVariable Long id) {
		return service.update(obj, id);
	}
	
	@DeleteMapping (value = "customers/{id}")
	@ApiOperation (value = "Delete one User based on id", notes = "Returns the deleted User record", response = User.class)
	public Optional<User> delete(@PathVariable Long id) {
		return service.delete(id);
	}
}
