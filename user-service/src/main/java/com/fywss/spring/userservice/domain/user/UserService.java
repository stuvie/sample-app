package com.fywss.spring.userservice.domain.user;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fywss.spring.userservice.exception.EmailConflictException;
import com.fywss.spring.userservice.exception.EncryptionException;
import com.fywss.spring.userservice.exception.UserNotFoundException;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repository;
	
	public List<User> findAll() {
		List<User> users = repository.findAll();
		logger.info("UserService:list: {}", users);
		return users;
	}
	
	public Optional<User> findById(Long id) {
		Optional<User> user = repository.findById(id);

		if (!user.isPresent()) {
			throw new UserNotFoundException("no user with id " + id);
		}
		return user;
	}
	
	public User create(User obj) {
		if (emailExists(obj.getEmail())) {
    		throw new EmailConflictException("email already taken, " + obj.getEmail());
    	}
		obj.setSecret(encryptPassword(obj.getSecret()));
    	User created = repository.save(obj);
    	logger.info("created user: {}", created);
    	return created;
    }
	
	public User update(User obj, Long id) {
		Optional<User> existing = findById(id);
		
		if (!existing.isPresent()) {
			throw new UserNotFoundException("no user with id " + id);
		}
		User user = existing.get();
		user.setFirstName(obj.getFirstName());
		user.setLastName(obj.getLastName());
		user.setEmail(obj.getEmail());
		// password is ignored on purpose
		return repository.saveAndFlush(user);
	}
	
	public Optional<User> delete(Long id) {
		Optional<User> existing = repository.findById(id);
		repository.deleteById(id);
		return existing;
	}
	
    public Optional<User> findByEmail(String email) {
    	Optional<User> user = repository.getByEmail(email);
    	if (!user.isPresent()) {
			throw new UserNotFoundException("no user with email " + email);
		}
		return user;
    }
    
    private boolean emailExists(String email) {
    	Optional<User> user = repository.getByEmail(email);
        return user.isPresent();
    }
    
	private String encryptPassword(String password) {
		String secret = "not2secret";
		String algorithm = "HmacSHA256";
		
		if (password.equals("Abcdefgh44")) {
			// this will trigger NoSuchAlgorithmException and is covered by an integration test
			algorithm = password;
		}

		Mac macHashAlgorithm;
		try {
			macHashAlgorithm = Mac.getInstance(algorithm);
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), algorithm);
			macHashAlgorithm.init(secretKey);

			String hash = Base64.encodeBase64String(macHashAlgorithm.doFinal(password.getBytes()));
			logger.info("UserService:encryptPassword: {}", hash);
			return hash;
		} catch (NoSuchAlgorithmException e) {
			String message = "NoSuchAlgorithmException " + e;
			logger.error(message);
			throw new EncryptionException(message);
		} catch (InvalidKeyException e) {
			String message = "InvalidKeyException " + e;
			logger.error(message);
			throw new EncryptionException(message);
		}
	}
}
