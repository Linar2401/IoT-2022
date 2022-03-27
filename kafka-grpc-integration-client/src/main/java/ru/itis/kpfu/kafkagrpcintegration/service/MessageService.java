package ru.itis.kpfu.kafkagrpcintegration.service;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.itis.kpfu.protobufcommon.MessageRequest;
import ru.itis.kpfu.protobufcommon.MessageServiceGrpc;


/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@Service
public class MessageService {

    @GrpcClient("grpc-server")
    private MessageServiceGrpc.MessageServiceBlockingStub messageStub;

    public void sendMessage(String message) {
        var request = MessageRequest.newBuilder()
                .setMessage(message)
                .build();
        var response = messageStub.transport(request);
        log.info("Received response from server: {}", response.getMessage());
    }
}
