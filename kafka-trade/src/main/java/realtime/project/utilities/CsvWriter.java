package realtime.project.utilities;

import lombok.extern.slf4j.Slf4j;
import realtime.project.model.TradeData;
import realtime.project.model.TradeMetadata;
import  java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class CsvWriter {

    private static final String headerList = "UUID;PROCESSED AT;KAFKA TOPIC;KAFKA PARTITION;KAFKA OFFSET;SHARE NAME;TRADE DATE;OPEN PRICE;LOW PRICE;HIGH PRICE;ADJ CLOSE PRICE;TRADE VOLUME";

    private static final String PROCESSED_TRADE_FILE_PATH = "C:\\Users\\milli\\OneDrive\\MBA_Project2\\" +
            "Development\\KafkaProject\\results\\TradesToDate.csv";

    public static void writeMetadata(TradeMetadata metadata) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROCESSED_TRADE_FILE_PATH, true))) {

            if (new java.io.File(PROCESSED_TRADE_FILE_PATH).length() == 0) {
                writer.write(headerList + "\n");
            }
            TradeData ts = metadata.getData();

            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s%n",
                    metadata.getTradeUUID(),
                    metadata.getEventTimeStamp(),
                    metadata.getTopic(),
                    metadata.getPartition(),
                    metadata.getOffset(),
                    ts.getStockName(),
                    ts.getTradeDate(),
                    ts.getOpenPrice(),
                    ts.getLowPrice(),
                    ts.getHighPrice(),
                    ts.getAdjacentClosePrice(),
                    ts.getTradeVolume()
            ));
        } catch (IOException | RuntimeException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
