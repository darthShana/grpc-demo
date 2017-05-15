package com.darthshana.dto;

/**
 * Created by dharshanar on 13/05/17.
 */
public class MessageResponse {

    private CharacterMessage characterMessage;

    private MessageResponse(){}

    public MessageResponse(CharacterMessage characterMessage) {
        this.characterMessage = characterMessage;
    }

    public CharacterMessage getCharacterMessage(){
        return characterMessage;
    }
}
