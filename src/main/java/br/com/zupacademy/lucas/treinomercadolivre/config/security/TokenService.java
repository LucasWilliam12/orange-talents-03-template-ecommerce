package br.com.zupacademy.lucas.treinomercadolivre.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private String expirationInMillis;

	public String gerarToken(Authentication auth) {
		UserDetails user = (UserDetails) auth.getPrincipal();
		final Date now = new Date();
		final Date expiration = new Date(now.getTime() + Long.parseLong(this.expirationInMillis));

		return Jwts.builder().setIssuer("Desafio jornada dev eficiente mercado livre").setSubject(user.getUsername())
				.setIssuedAt(now).setExpiration(expiration).signWith(SignatureAlgorithm.HS256, this.secret).compact();

	}

	public Boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public String getUsername(String token) {
		Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return body.getSubject();
	}
}
