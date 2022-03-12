package ru.itis.kpfu.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import ru.itis.kpfu.grpc.model.MathServiceGrpc;
import ru.itis.kpfu.grpc.model.MaxRequest;
import ru.itis.kpfu.grpc.observer.MaxResponseObserver;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Zagir Dingizbaev
 */
public class MaxClient extends RootClient {

    public MaxClient(ManagedChannel channel) {
        super(channel);
    }

    @Override
    public void call() {
        super.call();

        MathServiceGrpc.MathServiceStub mathClient = MathServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<MaxRequest> requestObserver = mathClient.maxNumber(new MaxResponseObserver(latch));
        logger.info("Enter the series for which you want to calculate the max value (whitespace delimiter): ");
        Arrays.stream(scanner.nextLine().split(" "))
                .map(Integer::valueOf)
                .collect(Collectors.toList())
                .forEach(
                        number -> {
                            var req = MaxRequest.newBuilder()
                                    .setNumber(number)
                                    .build();
                            requestObserver.onNext(req);
                        }
                );
        requestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Thread interruption", e.getCause());
            Thread.currentThread().interrupt();
        }
    }
}
