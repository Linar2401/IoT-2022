package ru.itis.kpfu.grpc.client;

import io.grpc.ManagedChannel;
import ru.itis.kpfu.grpc.model.FactorRequest;
import ru.itis.kpfu.grpc.model.MathServiceGrpc;

/**
 * @author Zagir Dingizbaev
 */
public class FactoryClient extends RootClient {

    public FactoryClient(ManagedChannel channel) {
        super(channel);
    }

    @Override
    public void call() {
        super.call();

        MathServiceGrpc.MathServiceBlockingStub factoryClient = MathServiceGrpc.newBlockingStub(channel);
        logger.info("Please enter the number you want to factorize: ");
        FactorRequest factoryRequest = FactorRequest.newBuilder()
                .setNumber(scanner.nextInt())
                .build();

        logger.info("Send factory request: {}", factoryRequest);
        factoryClient.numberFactors(factoryRequest)
                .forEachRemaining(factoryResponse -> logger.info("Factory response: {}", factoryResponse.getResponse()));
    }
}
