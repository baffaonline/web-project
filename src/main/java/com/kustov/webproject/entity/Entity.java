package com.kustov.webproject.entity;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
