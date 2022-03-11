package ru.itis.kpfu.grpc.client;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import ru.itis.kpfu.grpc.MathServiceGrpc;
import ru.itis.kpfu.grpc.SQRTRequest;
import ru.itis.kpfu.grpc.SQRTResponse;

/**
 * @author Zagir Dingizbaev
 */

public class SQRTClient extends RootClient{

    public SQRTClient(ManagedChannel channel) {
        super(channel);
    }

    public void call() {
        logger.info("Calling {}", this.getClass().getSimpleName());

        MathServiceGrpc.MathServiceBlockingStub mathClient = MathServiceGrpc.newBlockingStub(channel);
        SQRTRequest request = SQRTRequest.newBuilder()
                .setNumber(4)
                .build();

        SQRTResponse response =  mathClient.squareRoot(request);
        logger.debug("Response has been received from server: {}", response);
    }
}
