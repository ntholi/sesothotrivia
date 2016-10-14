package com.nalaneholdings.sesothotrivia.model.bean;

import java.util.UUID;

/**
 * This class makes sure that all it's subclasses contains a unique ID generated through the
 * Universal Unique ID (UUID)
 * Created by ntholi.nkhatho on 2016/10/14.
 */

public abstract class ID {

    protected String id;

    public ID(String id) {
        setId(id);
    }

    public ID(){

    }

    public String getId() {
        if(id == null){
            id = generateID();
        }
        return id;
    }

    public void setId(String id) {
        if(id == null){
            id = generateID();
        }
        this.id = id;
    }

    private String generateID(){
        return UUID.randomUUID().toString();
    }
}
