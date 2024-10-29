package org.example.station1_animalregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = "org.example.station1_animalregistration")
public class Station1AnimalRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Station1AnimalRegistrationApplication.class, args);
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
