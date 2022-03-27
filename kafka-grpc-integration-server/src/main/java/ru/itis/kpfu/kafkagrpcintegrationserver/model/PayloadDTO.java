package ru.itis.kpfu.kafkagrpcintegrationserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.kpfu.protobufcommon.MessageRequest;

import java.time.Instant;

/**
 * @author Zagir Dingizbaev
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDTO {

    //Просто чтобы отослать побольше инфы, а не гонять одно и тоже сообщение
    private Long timestamp;
    private String message;
}
