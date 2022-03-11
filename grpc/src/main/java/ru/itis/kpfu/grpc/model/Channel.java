package ru.itis.kpfu.grpc.model;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Data;

/**
 * @author Zagir Dingizbaev
 */

@Data
public class Channel {
    private final ManagedChannel channel;

    public Channel(String address, int port) {
        this.channel = ManagedChannelBuilder.forAddress(address, port)
                .usePlaintext()
                .build();
    }

}
