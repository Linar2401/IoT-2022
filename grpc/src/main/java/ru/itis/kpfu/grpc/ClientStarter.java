package ru.itis.kpfu.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.itis.kpfu.grpc.client.DeviationClient;
import ru.itis.kpfu.grpc.client.SQRTClient;

/**
 * @author Zagir Dingizbaev
 */
public class ClientStarter {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5051)
                .usePlaintext()
                .build();

        SQRTClient sqrtClient = new SQRTClient(channel);
        sqrtClient.call();

        DeviationClient deviationClient = new DeviationClient(channel);
        deviationClient.call();

        channel.shutdown();
    }
}
