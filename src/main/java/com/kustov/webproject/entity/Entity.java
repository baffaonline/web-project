package com.kustov.webproject.entity;

import java.io.Serializable;

public class Entity implements Serializable, Cloneable{
    private int id;
    Entity(){
    }

    Entity(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
