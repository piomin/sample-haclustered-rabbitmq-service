package pl.piomin.services.rabbit.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.rabbit.commons.message.Order;
import pl.piomin.services.rabbit.commons.message.OrderType;

import javax.annotation.PostConstruct;
import java.util.Random;

@SpringBootApplication
public class Sender {
	
	private static final Logger logger = LoggerFactory.getLogger("Sender");
	
	@Autowired
	RabbitTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(Sender.class, args);
	}

	@PostConstruct
	public void send() {
		for (int i = 0; i < 1000; i++) {
			int id = new Random().nextInt(100000);
			template.convertAndSend(new Order(id, "TEST"+id, OrderType.values()[(id%2)]));
		}
		logger.info("Sending completed.");
	}
    
    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange("ex.example");
        return rabbitTemplate;
    }
    
}
