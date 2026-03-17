package com.example.lab4;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cadou implements Serializable {
    private String message;
    private boolean wrapped;
    private int weight;
    private Objects objectType;
    private Date deliveryDate;

    public Cadou() {
    }

    public Cadou(String message, boolean wrapped, int weight, Objects objectType, Date deliveryDate) {
        this.message = message;
        this.wrapped = wrapped;
        this.weight = weight;
        this.objectType = objectType;
        this.deliveryDate = deliveryDate;
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

    public Date getDeliveryDate() {
        return deliveryDate;
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

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        String formattedDate = "-";
        if (deliveryDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            formattedDate = sdf.format(deliveryDate);
        }

        return "Mesaj: " + message +
                " | Împachetat: " + (wrapped ? "Da" : "Nu") +
                " | Greutate: " + weight + " g" +
                " | Tip: " + objectType +
                " | Data: " + formattedDate;
    }
}