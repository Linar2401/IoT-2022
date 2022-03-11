package ru.itis.kpfu.grpc.client;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zagir Dingizbaev
 */

@Data
@RequiredArgsConstructor
public abstract class RootClient implements GRPCClient {

    protected final ManagedChannel channel;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
