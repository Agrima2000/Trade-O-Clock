package realtime.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TradeData {
    @JsonProperty("Ticker")
    private String stockName;

    @JsonProperty("Date")
    private String tradeDate;

    @JsonProperty("Open")
    private String openPrice;

    @JsonProperty("High")
    private String highPrice;

    @JsonProperty("Low")
    private String lowPrice;

    @JsonProperty("Adj Close")
    private String adjacentClosePrice;

    @JsonProperty("Volume")
    private String tradeVolume;

}

