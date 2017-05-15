package com.darthshana.resource;

import com.darthshana.dto.CharacterMessage;
import com.darthshana.dto.MessageRequest;
import com.darthshana.dto.MessageResponse;
import com.darthshana.dto.Vector;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dharshanar on 13/05/17.
 */

@RestController
@RequestMapping("/character")
public class CharacterResource {

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "hello world!!";
    }

    @RequestMapping(path="", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse updateCharacter(@RequestBody MessageRequest input) {
        Vector possition = input.getCharacter().getPossition();
        System.out.println("received request:"+input.getCharacter().getId()+" pos:"+ possition);
        CharacterMessage characterMessage = new CharacterMessage(input.getCharacter().getId(), new Vector(possition.getX(), possition.getY(), possition.getZ()+10));
        return new MessageResponse(characterMessage);
    }

}
