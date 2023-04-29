package by.bsuir.linguaserver;

import by.bsuir.linguaserver.security.RsaKeys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeys.class)
public class LinguaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinguaServerApplication.class, args);
    }
}
