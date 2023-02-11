package com.ems.base.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import lombok.extern.log4j.Log4j2;

/**
 * Message Configuration
 * 
 * @return
 */
@Log4j2
@Configuration
public class MessangeConfig {
	@Bean
	public MessageSource messageSource() {
		log.info("Message Source Configured");
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}