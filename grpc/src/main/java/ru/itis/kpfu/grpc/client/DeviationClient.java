package ru.itis.kpfu.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import ru.itis.kpfu.grpc.model.DeviationRequest;
import ru.itis.kpfu.grpc.model.MathServiceGrpc;
import ru.itis.kpfu.grpc.observer.DeviationResponseObserver;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Zagir Dingizbaev
 */
public class DeviationClient extends RootClient {

    public DeviationClient(ManagedChannel channel) {
        super(channel);
    }

    @Override
    public void call() {
        super.call();

        MathServiceGrpc.MathServiceStub mathClient = MathServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<DeviationRequest> requestObserver = mathClient.standardDeviation(new DeviationResponseObserver(latch));

        logger.info("Enter the series for which you want to calculate the deviation: ");
        requestObserver.onNext(DeviationRequest.newBuilder()
                .addAllData(Arrays.stream(scanner.nextLine().split(" ")).map(Integer::valueOf).collect(Collectors.toList()))
                .build());
        requestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Thread interruption", e.getCause());
            Thread.currentThread().interrupt();
        }
    }
}
