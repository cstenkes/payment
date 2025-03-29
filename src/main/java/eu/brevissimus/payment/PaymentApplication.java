package eu.brevissimus.payment;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class PaymentApplication {

	@Value("${application.timezone}")
	private String applicationTimeZone;

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

	@PostConstruct
	public void executeAfterMain() {
        log.info("default time zone:{}", applicationTimeZone);
		TimeZone.setDefault(TimeZone.getTimeZone(applicationTimeZone));
	}
}
