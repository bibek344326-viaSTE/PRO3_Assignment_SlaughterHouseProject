package org.example.productanimalinfoservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = "org.example.productanimalinfoservice")
public class ProductAnimalInfoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductAnimalInfoServiceApplication.class, args);
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
