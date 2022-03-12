package ru.itis.kpfu.grpc.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itis.kpfu.grpc.*;
import ru.itis.kpfu.grpc.observer.DeviationRequestObserver;

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
        return new DeviationRequestObserver(logger, responseObserver);
    }
}
