package pandey.rohit.streaming.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
	
		private String kafkaTopic;
		private ProducerWrapper producer;
		
		private static Long keyCounter = 0L;
		
		@Autowired
		public void setKafkaTopic(@Value("${kafka.producer.topic}") String kafkaTopic) {
			this.kafkaTopic = kafkaTopic;
		}
		
		@Autowired
		public void setProducer(ProducerWrapper producer) {
			this.producer = producer;
		}
		
		public void pushToKafka(String recordString) {
			ProducerRecord<Long, String> record = 
			new ProducerRecord<Long, String>(kafkaTopic, keyCounter, recordString);
			producer.sendRecord(record);
			keyCounter++;
		}
}
