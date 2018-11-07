package cn.decentchina.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 唐全成
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class ManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}
}
