package pandey.rohit.streaming;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import pandey.rohit.streaming.websocket.MeetupWebSocketHandler;

@SpringBootApplication
public class MeetupRsvpsToKafkaConnectorApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MeetupRsvpsToKafkaConnectorApplication.class, args);
	}
	
    @Bean
    public ApplicationRunner initializeConnection(
        MeetupWebSocketHandler meetupWebSocketHandler, 
        @Value("${meetup.ws.endpoint}") String MEETUP_RSVPS_ENDPOINT) {
           return args -> {
               WebSocketClient rsvpsSocketClient = new StandardWebSocketClient();

               rsvpsSocketClient.doHandshake(
            		   meetupWebSocketHandler, MEETUP_RSVPS_ENDPOINT);           
           };
    }

}
