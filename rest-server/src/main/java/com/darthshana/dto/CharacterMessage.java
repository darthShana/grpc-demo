package com.darthshana.dto;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Created by dharshanar on 13/05/17.
 */
public class CharacterMessage {

    UUID id;
    HashMap<String, String> objectives;
    Set<String> units;
    UUID commander;
    Vector possition;
    Vector orientation;

    private CharacterMessage(){}

    public CharacterMessage(UUID id, Vector posstion){
        this.id = id;
        this.possition = posstion;
    }

    public UUID getId() {
        return id;
    }

    public Vector getPossition() {
        return possition;
    }


}
