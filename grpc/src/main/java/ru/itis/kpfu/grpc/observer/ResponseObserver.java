package ru.itis.kpfu.grpc.observer;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itis.kpfu.grpc.model.DeviationResponse;

import java.util.concurrent.CountDownLatch;

/**
 * @author Zagir Dingizbaev
 */

@RequiredArgsConstructor
public abstract class ResponseObserver<T> implements StreamObserver<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    protected final CountDownLatch latch;

    @Override
    public void onNext(T response) {
        logger.info("Received a response from the server: {}", response);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("Error during handling gRPC response", throwable);
    }

    @Override
    public void onCompleted() {
        logger.info("Server has completed task");
        latch.countDown();
    }
}
