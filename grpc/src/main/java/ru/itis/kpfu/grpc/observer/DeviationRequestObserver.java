package ru.itis.kpfu.grpc.observer;

import io.grpc.stub.StreamObserver;
import ru.itis.kpfu.grpc.model.DeviationRequest;
import ru.itis.kpfu.grpc.model.DeviationResponse;

/**
 * @author Zagir Dingizbaev
 */

public class DeviationRequestObserver extends RequestObserver<DeviationRequest> {

    private final StreamObserver<DeviationResponse> responseObserver;
    private Double deviation = 0.0;
    private Double total = 0.0;

    public DeviationRequestObserver(StreamObserver<DeviationResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(DeviationRequest deviationRequest) {
        var dataList = deviationRequest.getDataList();
        total += dataList.stream()
                .mapToInt(Integer::intValue)
                .sum();
        double mean = total / dataList.size();
        for (int num : dataList) {
            deviation += Math.pow(num - mean, 2);
        }

        deviation = Math.sqrt(deviation / dataList.size());
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error("Exception during gRPC deviationRequest handling", throwable);
    }

    @Override
    public void onCompleted() {
        var resp = DeviationResponse.newBuilder()
                .setDeviation(deviation)
                .build();
        logger.info("Send result: {}", deviation);
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
