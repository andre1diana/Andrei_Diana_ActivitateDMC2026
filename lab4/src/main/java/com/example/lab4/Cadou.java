package com.example.lab4;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cadou implements Parcelable {
    private String message;
    private boolean wrapped;
    private int weight;
    private Objects objectType;
    private Date deliveryDate;

    public Cadou() {
    }


    protected Cadou(Parcel in) {
        message = in.readString();
        wrapped = in.readByte() != 0;
        weight = in.readInt();
        objectType = Objects.valueOf(in.readString());
        long time = in.readLong();
        deliveryDate = time == -1 ? null : new Date(time);
    }

    public static final Creator<Cadou> CREATOR = new Creator<Cadou>() {
        @Override
        public Cadou createFromParcel(Parcel in) {
            return new Cadou(in);
        }

        @Override
        public Cadou[] newArray(int size) {
            return new Cadou[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeByte((byte) (wrapped ? 1 : 0));
        dest.writeInt(weight);
        dest.writeString(objectType.name());
        dest.writeLong(deliveryDate != null ? deliveryDate.getTime() : -1);
    }

    @Override
    public int describeContents() {
        return 0;
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