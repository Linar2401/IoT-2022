package ru.itis.kpfu.grpc.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itis.kpfu.grpc.MathServiceGrpc;
import ru.itis.kpfu.grpc.SQRTRequest;
import ru.itis.kpfu.grpc.SQRTResponse;

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
}
