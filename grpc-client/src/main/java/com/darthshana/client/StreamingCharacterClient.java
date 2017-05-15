package com.darthshana.client;

import com.darthshana.dto.*;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.darthshana.client.SimpleCharacterClient.asBytes;

/**
 * Created by dharshanar on 13/05/17.
 */
public class StreamingCharacterClient {

    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext(true)
                .build();

        CharacterServiceGrpc.CharacterServiceStub stub = CharacterServiceGrpc.newStub(channel);

        AtomicInteger count = new AtomicInteger();
        long startTime = System.currentTimeMillis();

        StreamObserver<MessageResponse> responseObserver = new StreamObserver<MessageResponse>() {
            @Override
            public void onNext(MessageResponse messageResponse) {
                System.out.println("message received:"+messageResponse);
                if(count.addAndGet(1)>=1000){
                    System.out.println("all done!!!"+(System.currentTimeMillis()-startTime));

                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
            }
        };

        StreamObserver<MessageRequest> requestObserver = stub.updateCharacterStream(responseObserver);

        Set<UUID> uuids = IntStream.range(0, 1000).mapToObj(i -> UUID.randomUUID()).collect(Collectors.toSet());

        uuids.stream().forEach(id->{
            requestObserver.onNext(MessageRequest.newBuilder().setCharacterMessage(
                    CharacterMessage.newBuilder().setId(ByteString.copyFrom(asBytes(id))).build())
                    .build());
        });

        Thread.sleep(5000);
    }

    private static MessageRequest createMessage(UUID id) {
        return MessageRequest.newBuilder()
                .setCharacterMessage(
                        CharacterMessage.newBuilder()
                                .setId(ByteString.copyFrom(asBytes(id)))
                                .setPosstion(Vector.newBuilder()
                                        .setX(10).setY(20).setZ(30).build())
                                .build())
                .build();
    }

}
