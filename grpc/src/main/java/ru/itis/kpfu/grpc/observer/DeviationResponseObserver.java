package ru.itis.kpfu.grpc.observer;

import ru.itis.kpfu.grpc.model.DeviationResponse;

import java.util.concurrent.CountDownLatch;

/**
 * @author Zagir Dingizbaev
 */


public class DeviationResponseObserver extends ResponseObserver<DeviationResponse> {
    public DeviationResponseObserver(CountDownLatch latch) {
        super(latch);
    }

    @Override
    public void onNext(DeviationResponse response) {
        logger.debug("Received deviation from server: {}", response.getDeviation());
    }
}
