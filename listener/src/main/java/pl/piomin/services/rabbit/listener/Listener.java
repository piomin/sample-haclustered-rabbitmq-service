package pl.piomin.services.rabbit.listener;

import java.util.logging.Logger;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.piomin.services.rabbit.commons.message.Order;

@SpringBootApplication
@EnableRabbit
public class Listener {

	private static Logger logger = Logger.getLogger("Listener");

	public static void main(String[] args) {
		SpringApplication.run(Listener.class, args);
	}

	@RabbitListener(queues = "q.example")
	public void onMessage(Order order) {
		logger.info(order.toString());
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		connectionFactory.setAddresses("192.168.99.100:30000,192.168.99.100:30002,,192.168.99.100:30004");
		connectionFactory.setChannelCacheSize(10);
		return connectionFactory;
	}
	
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(20);
        return factory;
    }

	@Bean
	public Queue queue() {
		return new Queue("q.example");
	}

}
