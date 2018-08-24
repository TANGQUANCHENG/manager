package cn.decentchina.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.decent.component","cn.decentchina.manager"})
public class ManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}
}
