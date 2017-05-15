package com.darthshana.client;

import com.darthshana.dto.*;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.nio.ByteBuffer;
import java.util.UUID;


/**
 * Created by dharshanar on 12/05/17.
 */
public class SimpleCharacterClient {

    public static void main(String[] args){
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext(true)
                .build();

        CharacterServiceGrpc.CharacterServiceBlockingStub stub = CharacterServiceGrpc.newBlockingStub(channel);
        long startTime = System.currentTimeMillis();

        for(int i=0;i<1000;i++) {
            MessageResponse response = stub.updateCharacter(
                    MessageRequest.newBuilder()
                            .setCharacterMessage(
                                    CharacterMessage.newBuilder()
                                            .setId(ByteString.copyFrom(asBytes(UUID.randomUUID())))
                                            .setPosstion(Vector.newBuilder()
                                                    .setX(10).setY(20).setZ(30).build())
                                            .build())
                            .build());

            System.out.println("response:" + response.getCharacterMessage().getPosstion());
        }

        System.out.println("finished message transfer:"+(System.currentTimeMillis()-startTime));
    }

    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
