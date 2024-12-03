package realtime.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import realtime.project.service.KafkaProducerService;
import realtime.project.model.TradeData;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/kafka")
public class TradeController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public static final String PATH_DIRECTORY = "C:\\Users\\milli\\MBA_Project2\\Development\\KafkaProject\\input\\";

    @PostMapping("/publishJson")
    public ResponseEntity<String> publishJson(@RequestBody String tradeYear) throws IOException {

        String tradeFileName = PATH_DIRECTORY + tradeYear+"_Global_Markets_Data.json";

        log.info("Trade File to process : {}", tradeFileName);

        ObjectMapper objectMapper = new ObjectMapper();
        List<TradeData> tradesToPublish = objectMapper.readValue(
                new File(tradeFileName),
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, TradeData.class));

        for(TradeData td : tradesToPublish){
            kafkaProducerService.sendMessage(td);
        }

        return ResponseEntity.ok(" processed records size is : "+ tradesToPublish.size());
    }

    
}
