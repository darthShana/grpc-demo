package com.darthshana.dto;

/**
 * Created by dharshanar on 13/05/17.
 */
public class MessageRequest {

    private CharacterMessage character;

    private MessageRequest(){}

    public MessageRequest(CharacterMessage characterMessage) {
        this.character = characterMessage;
    }

    public CharacterMessage getCharacter() {
        return character;
    }
}
