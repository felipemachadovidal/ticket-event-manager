package com.compassuol.ms_event_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFeignClients
@SpringBootApplication
public class MsEventManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEventManagerApplication.class, args);
	}

}
