package org.example.station2_cuttingandpartsregistration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Station2CuttingAndPartsRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Station2CuttingAndPartsRegistrationApplication.class, args);
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
