package misoya.healing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class HealingApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealingApplication.class, args);
	}

}
