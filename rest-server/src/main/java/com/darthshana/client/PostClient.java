package com.darthshana.client;

import com.darthshana.dto.CharacterMessage;
import com.darthshana.dto.MessageRequest;
import com.darthshana.dto.MessageResponse;
import com.darthshana.dto.Vector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * Created by dharshanar on 13/05/17.
 */
public class PostClient {


    public static void main(String[] args){
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        ObjectMapper mapper = new ObjectMapper();

        try {
            long startTime = System.currentTimeMillis();
            for(int i=0;i<1000;i++) {
                MessageRequest value = new MessageRequest(new CharacterMessage(UUID.randomUUID(), new Vector(10, 20, 30)));

                HttpPost request = new HttpPost("http://localhost:8080/character");
                StringEntity params = new StringEntity(mapper.writeValueAsString(value));
                request.addHeader("content-type", "application/json");
                request.addHeader("Accept", "application/json");

                request.setEntity(params);
                HttpResponse response = httpClient.execute(request);
                BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

                String output;
                StringBuilder sb = new StringBuilder();
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                MessageResponse mr = mapper.readValue(sb.toString(), MessageResponse.class);
                System.out.println("Output from Server .... \n");
                System.out.println(mr.getCharacterMessage().getPossition());
            }

            System.out.println("all done!!:"+(System.currentTimeMillis()-startTime));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
