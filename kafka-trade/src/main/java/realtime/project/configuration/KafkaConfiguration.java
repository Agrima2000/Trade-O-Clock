package realtime.project.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic createJsonTopic(){
        return TopicBuilder.name("kafka-json-topic-1")
                .partitions(5)
                .build();
    }
}
