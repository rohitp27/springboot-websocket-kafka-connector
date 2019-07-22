package pandey.rohit.streaming.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

@Component
public class ProducerWrapper {
	
		private Producer<Long, String> kafkaProducer;
		private Long messageCount;

		@Autowired
		public ProducerWrapper(
				@Value("${kafka.producer.client-id}") String clientId,				
				@Value("${kafka.producer.bootstrap-server}") String bootstrapServer,
				@Value("${kafka.producer.value-serializer}") String valueSerializer,
				@Value("${kafka.producer.key-serializer}") String keySerializer
				) {
			Properties kafkaProperties = getProperties(bootstrapServer, clientId, valueSerializer, keySerializer);
			kafkaProducer = new KafkaProducer<>(kafkaProperties);
			messageCount = 0L;
		}
		
		private void incrementCount() {
			if(messageCount%100 == 0) System.out.println("Produced : " + messageCount);
			messageCount++;
		}
		
		public void sendRecord(ProducerRecord<Long, String> record) {
			kafkaProducer.send(record);
			incrementCount();
		}
			
		private Properties getProperties(String bootstrapServers, String clientId, String valueSerializer, String keySerializer) {
			Properties properties = new Properties();
			properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
			properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
			properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
			properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
			return properties;
		}
}
