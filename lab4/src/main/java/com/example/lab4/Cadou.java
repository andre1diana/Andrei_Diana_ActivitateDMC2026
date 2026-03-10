package com.example.lab4;

import java.io.Serializable;

public class Cadou implements Serializable {
    private String message;
    private boolean wrapped;
    private int weight;
    private Objects objectType;

    public Cadou() {
    }

    public Cadou(String message, boolean opened, int weight, Objects objectType) {
        this.message = message;
        this.wrapped = opened;
        this.weight = weight;
        this.objectType = objectType;
    }

    public String getMessage() {
        return message;
    }

    public boolean isWrapped() {
        return wrapped;
    }

    public int getWeight() {
        return weight;
    }

    public Objects getObjectType() {
        return objectType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setWrapped(boolean wrapped) {
        this.wrapped = wrapped;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setObjectType(Objects objectType) {
        this.objectType = objectType;
    }

    @Override
    public String toString() {
        return "Cadou{" +
                "message='" + message + '\'' +
                ", wrapped=" + wrapped +
                ", weight=" + weight +
                ", objectType=" + objectType +
                '}';
    }
}