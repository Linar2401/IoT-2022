package ru.itis.kpfu.grpc.observer;

import io.grpc.stub.StreamObserver;
import ru.itis.kpfu.grpc.model.MaxRequest;
import ru.itis.kpfu.grpc.model.MaxResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zagir Dingizbaev
 */
public class MaxRequestObserver extends RequestObserver<MaxRequest> {

    private final StreamObserver<MaxResponse> responseObserver;
    private final List<Integer> numbers = new ArrayList<>();

    public MaxRequestObserver(StreamObserver<MaxResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(MaxRequest maxRequest) {
        Integer number = maxRequest.getNumber();
        numbers.add(number);
        var result = numbers.stream().mapToInt(Integer::intValue).max().orElse(Integer.MIN_VALUE);

        MaxResponse resp = MaxResponse.newBuilder()
                .setNumber(result)
                .build();

        logger.info("Sending result: {}", resp);
        responseObserver.onNext(resp);
    }
}
