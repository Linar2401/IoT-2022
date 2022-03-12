package ru.itis.kpfu.grpc.observer;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import ru.itis.kpfu.grpc.DeviationResponse;

import java.util.concurrent.CountDownLatch;

/**
 * @author Zagir Dingizbaev
 */

@RequiredArgsConstructor
public class DeviationResponseObserver implements StreamObserver<DeviationResponse> {

    private final Logger logger;
    private final CountDownLatch latch;

    @Override
    public void onNext(DeviationResponse deviationResponse) {
        logger.debug("Received a response from the server: {}", deviationResponse.getDeviation());
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("Error during handling gRPC response", throwable);
    }

    @Override
    public void onCompleted() {
        logger.debug("Server has completed evaluating deviation");
        latch.countDown();
    }
}
