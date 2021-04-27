package br.com.zupacademy.lucas.treinomercadolivre.utils;

import org.springframework.stereotype.Component;

@Component
public class EnvioEmailFake implements EnvioEmail{

	@Override
	public void sendEmail(String toEmail, String fromEmail, String body) {
		System.out.println("to: ["+toEmail+"], from: ["+fromEmail+"], body: ["+body+"]");
	}
	
}
