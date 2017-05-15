package com.darthshana.server;

import com.darthshana.dto.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * Created by dharshanar on 12/05/17.
 */
public class CharacterServer {

    public static void main(String[] args) throws InterruptedException, IOException {

        Server server = ServerBuilder.forPort(8080).addService(new CharacterServiceImpl()).build();

        System.out.println("Starting server......");
        server.start();
        System.out.println("Server started...");
        server.awaitTermination();

    }

    public static class CharacterServiceImpl extends CharacterServiceGrpc.CharacterServiceImplBase{

        @Override
        public void updateCharacter(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
            System.out.println("received request:"+request);

            CharacterMessage characterMessage = request.getCharacterMessage();
            Vector posstion = characterMessage.getPosstion();

            MessageResponse response = MessageResponse.newBuilder()
                    .setCharacterMessage(CharacterMessage.newBuilder()
                            .setId(characterMessage.getId())
                            .setPosstion(Vector.newBuilder()
                                    .setX(posstion.getX()).setY(posstion.getY()).setZ(posstion.getZ()+10)
                                    .build())
                            .build())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public StreamObserver<MessageRequest> updateCharacterStream(StreamObserver<MessageResponse> responseObserver) {
            return new StreamObserver<MessageRequest>() {
                @Override
                public void onNext(MessageRequest messageRequest) {
                    System.out.println("received request:"+messageRequest);
                    responseObserver.onNext(MessageResponse.newBuilder()
                            .setCharacterMessage(
                                    CharacterMessage.newBuilder()
                                            .setId(messageRequest.getCharacterMessage().getId())
                                            .setPosstion(Vector.newBuilder().setX(10).setY(20).setZ(40))
                                            .build()).build());
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {

                }
            };
        }
    }
}
