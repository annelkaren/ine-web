package com.svi.app;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class IneServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IneServiceApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    
	    mailSender.setUsername("qskconsulting@gmail.com");
	    mailSender.setPassword("pqjo bimh vosb swgz");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.host", "smtp.gmail.com");
	    props.put("mail.port", "587");
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.from.email", "qskconsulting@gmail.com");
	    
	    return mailSender;
	}
}
