package br.edu.atitus.pooavancado.atitusound.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.pooavancado.atitusound.entities.UserEntity;
import br.edu.atitus.pooavancado.atitusound.entities.dtos.LoginDTO;
import br.edu.atitus.pooavancado.atitusound.entities.dtos.UserDTO;
import br.edu.atitus.pooavancado.atitusound.services.UserService;
import br.edu.atitus.pooavancado.atitusound.utils.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthContoller {

	private final UserService service;
	private final AuthenticationConfiguration authConfig;

	public AuthContoller(UserService service, AuthenticationConfiguration authConfig) {
		super();
		this.service = service;
		this.authConfig = authConfig;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserEntity> postSignup(@RequestBody UserDTO dto) {
		var user = new UserEntity();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());

		try {
			service.save(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<String> postSignin(@RequestBody LoginDTO dto) {
		try {
			var auth = authConfig.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("erro", e.getMessage()).build();
		}
		return ResponseEntity.ok(JwtUtils.generateTokenFromUsername(dto.getUsername()));
	}
	
	
	
	
	
	
	
	
	
	

}
