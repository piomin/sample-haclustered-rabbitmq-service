package pl.piomin.services.rabbit.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.rabbit.commons.message.Order;

@SpringBootApplication
@EnableRabbit
public class Listener {

	private static final Logger LOGGER = LoggerFactory.getLogger("Listener");

	private Long timestamp;

	public static void main(String[] args) {
		SpringApplication.run(Listener.class, args);
	}

	@RabbitListener(queues = "q.example")
	public void onMessage(Order order) {
		if (timestamp == null)
			timestamp = System.currentTimeMillis();
		LOGGER.info((System.currentTimeMillis() - timestamp) + " : " + order.toString());
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setConcurrentConsumers(10);
		factory.setMaxConcurrentConsumers(20);
		return factory;
	}

}
