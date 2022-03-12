package ru.itis.kpfu.grpc.observer;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import ru.itis.kpfu.grpc.DeviationRequest;
import ru.itis.kpfu.grpc.DeviationResponse;

import java.util.List;

/**
 * @author Zagir Dingizbaev
 */

@RequiredArgsConstructor
public class DeviationRequestObserver implements StreamObserver<DeviationRequest> {

    private final Logger logger;
    private final StreamObserver<DeviationResponse> responseObserver;
    private Double deviation = 0.0;
    private Double total = 0.0;

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

        deviation = Math.sqrt(deviation/dataList.size());
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
        responseObserver.onNext(resp);
        logger.info("Send result: {}", deviation);
        responseObserver.onCompleted();
    }
}
