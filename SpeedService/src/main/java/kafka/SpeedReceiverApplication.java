package kafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SpeedReceiverApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(SpeedReceiverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Receiver is running and waiting for messages");
    }
}
