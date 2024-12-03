package realtime.project.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import realtime.project.model.TradeData;
import realtime.project.model.TradeMetadata;
import realtime.project.utilities.CsvWriter;

@Slf4j
@Component
@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @KafkaListener(topics = "kafka-json-topic-1", groupId = "myGroup")
    public void consume(TradeData record,
                        @Header("kafka_receivedTopic") String topic,
                        @Header("kafka_receivedPartitionId") int partition,
                        @Header("kafka_offset") long offset
                        ){
        TradeMetadata tradeMetadata = new TradeMetadata(
                topic,
                partition,
                offset,
                record
        );
        CsvWriter.writeMetadata(tradeMetadata);
        processKafkaMessage(tradeMetadata);
    }


    private void processKafkaMessage(TradeMetadata tradeMetadata) {
        LOGGER.info("Processing message at :{}, UUID : {}, topic: {}, partition: {}, offset: {}, value: {}",
                tradeMetadata.getEventTimeStamp(),
                tradeMetadata.getTradeUUID(),
                tradeMetadata.getTopic(),
                tradeMetadata.getPartition(),
                tradeMetadata.getOffset(),
                tradeMetadata.getData());

    }
}