package pandey.rohit.streaming.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import pandey.rohit.streaming.kafka.ProducerService;

@Component
public class MeetupWebSocketHandler extends AbstractWebSocketHandler {
	
	private ProducerService producerService;
	
	@Autowired
	public void setProducerService(ProducerService producerService) {
		this.producerService = producerService;
	}

    @Override
    public void handleMessage(WebSocketSession session,
            WebSocketMessage<?> message) {
    	producerService.pushToKafka(message.getPayload().toString());
    	System.out.println(message.getPayload());
    }
}