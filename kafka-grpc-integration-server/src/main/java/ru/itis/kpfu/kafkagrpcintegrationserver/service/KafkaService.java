package ru.itis.kpfu.kafkagrpcintegrationserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.kafkagrpcintegrationserver.model.PayloadDTO;
import ru.itis.kpfu.protobufcommon.MessageRequest;

import java.time.Instant;

import static ru.itis.kpfu.kafkagrpcintegrationserver.config.KafkaConstants.DEFAULT_SINGLE_FACTORY;
import static ru.itis.kpfu.kafkagrpcintegrationserver.config.KafkaConstants.MESSAGE_TOPIC;

/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

    /*Достаточно бесполезный учебный класс, чтобы показать что умеем пользоваться кафкой и gRPC
      Don't try this at home (work), так сказать
      На практике gRPC нужен для межсервисного взаимодействия при high load, а для перегона сообщений с микрух юзаем MQTT
    */
    private final KafkaTemplate<String, PayloadDTO> template;

    public void sendMessage(MessageRequest request) {
        template.send(MESSAGE_TOPIC, PayloadDTO.builder()
                .message(request.getMessage())
                .timestamp(Instant.now().toEpochMilli())
                .build());
    }

    @KafkaListener(id = "${kafka.message_listener:message_listener}",
            topics = {MESSAGE_TOPIC},
            containerFactory = DEFAULT_SINGLE_FACTORY,
            autoStartup = "${kafka.listener-auto-startup:true}")
    public void consume(PayloadDTO dto) {
        log.info("Received message from kafka topic {}: {}", MESSAGE_TOPIC, dto);
    }
}
