package ru.itis.kpfu.grpc.observer;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zagir Dingizbaev
 */

@RequiredArgsConstructor
public abstract class RequestObserver<T> implements StreamObserver<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public void onError(Throwable throwable) {
        logger.error("Exception during gRPC {} handling\n {}", this.getClass().getName(), throwable);
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onCompleted() {
        logger.info("Observer {} stream closed", this.getClass().getName());
    }
}
