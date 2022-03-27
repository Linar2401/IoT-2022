package ru.itis.kpfu.kafkagrpcintegrationserver.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.itis.kpfu.protobufcommon.MessageRequest;
import ru.itis.kpfu.protobufcommon.MessageResponse;
import ru.itis.kpfu.protobufcommon.MessageServiceGrpc;

/**
 * @author Zagir Dingizbaev
 */

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class MessageGrpcService extends MessageServiceGrpc.MessageServiceImplBase {

    private final KafkaService kafkaService;

    @Override
    public void transport(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
        MessageResponse reply = MessageResponse.newBuilder()
                .setMessage("Received: " + request.getMessage())
                .build();
        kafkaService.sendMessage(request);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
