package ru.itis.kpfu.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itis.kpfu.grpc.service.MathServiceImpl;

import java.io.IOException;

/**
 * @author Zagir Dingizbaev
 */
public class ServerStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerStarter.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(5051)
                .addService(new MathServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread (()->{
            LOGGER.info("Received Shutdown Request");
            server.shutdown();
            LOGGER.info("Successfully stopped the server");
        }));
        server.awaitTermination();
    }
}
