package realtime.project.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
public class TradeMetadata {
    private String topic;
    private int partition;
    private long offset;
    private TradeData data;
    private String tradeUUID;
    private LocalDateTime eventTimeStamp;

    public TradeMetadata(String topic, int partition, long offset, TradeData data) {
        this.tradeUUID = UUID.randomUUID().toString().toString().replace("-","");
        this.eventTimeStamp = LocalDateTime.now();
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.data=data;
    }
}
