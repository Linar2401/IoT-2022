package ru.itis.kpfu.kafkagrpcintegrationserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Zagir Dingizbaev
 */

@Data
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private String kafkaServer = "kafka:9092";
    private String kafkaProducerName = "default-producer-name";
    private Integer countOfListeners;
    private List<String> topics;
}