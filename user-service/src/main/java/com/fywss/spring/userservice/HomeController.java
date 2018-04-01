package com.fywss.spring.userservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fywss.spring.userservice.domain.user.User;
import com.fywss.spring.userservice.domain.user.UserRepository;

@Controller
public class HomeController {
	@Value("${info.message}")
	private String message;
	
	@Autowired
    private UserRepository userRepository;
	
	@RequestMapping("/")
	public String loadHome(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		model.addAttribute("message", message);
		return "home";
	}
	
	@RequestMapping("/actuatorlinks")
	public String loadActuatorLinks(Model model) {
		model.addAttribute("message", message);
		return "actuator";
	}
}
