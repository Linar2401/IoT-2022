package ru.itis.kpfu.grpc.client;

import io.grpc.ManagedChannel;
import ru.itis.kpfu.grpc.model.MathServiceGrpc;
import ru.itis.kpfu.grpc.model.SQRTRequest;
import ru.itis.kpfu.grpc.model.SQRTResponse;

/**
 * @author Zagir Dingizbaev
 */

public class SQRTClient extends RootClient {

    public SQRTClient(ManagedChannel channel) {
        super(channel);
    }

    @Override
    public void call() {
        super.call();

        MathServiceGrpc.MathServiceBlockingStub mathClient = MathServiceGrpc.newBlockingStub(channel);
        logger.info("Please, enter the number from which you want to extract the square root: ");
        SQRTRequest request = SQRTRequest.newBuilder()
                .setNumber(scanner.nextInt())
                .build();

        SQRTResponse response = mathClient.squareRoot(request);
        logger.info("Response has been received from server: {}", response);
    }
}
