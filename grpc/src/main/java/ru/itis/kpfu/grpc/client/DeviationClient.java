package ru.itis.kpfu.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import ru.itis.kpfu.grpc.*;
import ru.itis.kpfu.grpc.observer.DeviationResponseObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Zagir Dingizbaev
 */
public class DeviationClient extends RootClient {

    public DeviationClient(ManagedChannel channel) {
        super(channel);
    }

    public void call() {
        logger.info("Calling {}", this.getClass().getName());

        MathServiceGrpc.MathServiceStub mathClient = MathServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<DeviationRequest> requestObserver = mathClient.standardDeviation(new DeviationResponseObserver(logger, latch));

        requestObserver.onNext(DeviationRequest.newBuilder()
                .addAllData(List.of(1, 2, 3, 4))
                .build());
        requestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
