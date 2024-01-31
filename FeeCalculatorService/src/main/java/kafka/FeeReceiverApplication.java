package kafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class FeeReceiverApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(FeeReceiverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Receiver is running and waiting for messages");
    }
}
