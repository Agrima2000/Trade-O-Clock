package realtime.project.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import realtime.project.model.TradeData;

@Service
public class KafkaProducerService {
    private static  final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, TradeData> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, TradeData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(TradeData message){

        kafkaTemplate.send("kafka-json-topic-1", message);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Error during processing", e);
        }
    }
}
