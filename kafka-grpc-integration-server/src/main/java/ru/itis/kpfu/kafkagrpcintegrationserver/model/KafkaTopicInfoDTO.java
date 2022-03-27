package ru.itis.kpfu.kafkagrpcintegrationserver.model;

import lombok.Data;

/**
 * @author Zagir Dingizbaev
 */

@Data
public class KafkaTopicInfoDTO {
    private String name;
    private Integer partitions;
    private Integer replicas;

    public KafkaTopicInfoDTO(String definition) {
        String[] parts = definition.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Unprocessable topic definition: " + definition);
        }
        this.name = parts[0];
        this.partitions = Integer.valueOf(parts[1]);
        this.replicas = Integer.valueOf(parts[2]);
    }
}
