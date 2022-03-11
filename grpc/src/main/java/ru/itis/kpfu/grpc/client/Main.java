package ru.itis.kpfu.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Zagir Dingizbaev
 */
public class Main {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5051)
                .usePlaintext()
                .build();

        SQRTClient sqrtClient = new SQRTClient(channel);
        sqrtClient.call();

        channel.shutdown();
    }
}
