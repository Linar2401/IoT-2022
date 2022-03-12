package ru.itis.kpfu.grpc.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itis.kpfu.grpc.model.*;
import ru.itis.kpfu.grpc.observer.DeviationRequestObserver;
import ru.itis.kpfu.grpc.observer.MaxRequestObserver;

import java.lang.Math;

/**
 * @author Zagir Dingizbaev
 */
public class MathServiceImpl extends MathServiceGrpc.MathServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger("MathServiceLogger");

    @Override
    public void squareRoot(SQRTRequest request, StreamObserver<SQRTResponse> responseObserver) {
        logger.info("Calling square root service");

        SQRTResponse response = SQRTResponse.newBuilder()
                .setNumber(Math.sqrt(request.getNumber()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DeviationRequest> standardDeviation(StreamObserver<DeviationResponse> responseObserver) {
        return new DeviationRequestObserver(responseObserver);
    }

    @Override
    public void numberFactors(FactorRequest request, StreamObserver<FactorResponse> responseObserver) {
        int number = request.getNumber();
        for (int i = 1; i <= number; ++i) {
            if (number % i == 0) {
                var resp = FactorResponse.newBuilder()
                        .setResponse(i)
                        .build();
                responseObserver.onNext(resp);
            }
        }

        responseObserver.onCompleted();
        logger.info("Successfully completed sending responses to client");
    }

    @Override
    public StreamObserver<MaxRequest> maxNumber(StreamObserver<MaxResponse> responseObserver) {
        return new MaxRequestObserver(responseObserver);
    }
}
