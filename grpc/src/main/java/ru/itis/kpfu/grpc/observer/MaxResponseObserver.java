package ru.itis.kpfu.grpc.observer;

import ru.itis.kpfu.grpc.model.MaxResponse;

import java.util.concurrent.CountDownLatch;

/**
 * @author Zagir Dingizbaev
 */

public class MaxResponseObserver extends ResponseObserver<MaxResponse> {

    public MaxResponseObserver(CountDownLatch latch) {
        super(latch);
    }

    @Override
    public void onNext(MaxResponse response) {
        logger.info("Received max from server: {}", response.getNumber());
    }
}
