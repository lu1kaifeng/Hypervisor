package org.lu.hypervisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HypervisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HypervisorApplication.class, args);
	}

}
