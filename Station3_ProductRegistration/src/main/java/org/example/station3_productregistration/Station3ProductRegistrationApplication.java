package org.example.station3_productregistration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Station3ProductRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Station3ProductRegistrationApplication.class, args);
    }
    @Component
    public class AppStartupRunner implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            // Keep the application running
            synchronized (this) {
                wait();
            }
        }
    }
}
