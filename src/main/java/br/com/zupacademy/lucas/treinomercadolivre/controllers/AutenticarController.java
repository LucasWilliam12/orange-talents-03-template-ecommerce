package br.com.zupacademy.lucas.treinomercadolivre.controllers;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.LoggerFactory;

import br.com.zupacademy.lucas.treinomercadolivre.config.security.TokenService;
import br.com.zupacademy.lucas.treinomercadolivre.dto.TokenDto;
import br.com.zupacademy.lucas.treinomercadolivre.requests.LoginInputRequest;

@RestController
@RequestMapping("/login")
public class AutenticarController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	private static final Logger log = LoggerFactory.getLogger(AutenticarController.class);
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestBody LoginInputRequest request){
		
		UsernamePasswordAuthenticationToken authenticationToken = request.build();
		
		try {
			Authentication authentication = authManager.authenticate(authenticationToken);
			String token = tokenService.gerarToken(authentication);
			
			return ResponseEntity.ok(new TokenDto("Bearer", token));
		} catch (AuthenticationException e) {
			log.error("[Autenticacao] {}", e);
			return ResponseEntity.badRequest().build();
		}
		
	}
	
}
