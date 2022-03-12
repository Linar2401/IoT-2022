package ru.itis.kpfu.grpc.client;

import io.grpc.ManagedChannel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @author Zagir Dingizbaev
 */

@Data
@RequiredArgsConstructor
public abstract class RootClient implements GRPCClient {

    protected final ManagedChannel channel;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Scanner scanner = new Scanner(System.in);

    @Override
    public void call() {
        logger.info("Calling {}", this.getClass().getName());
    }
}
