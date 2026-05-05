package mta.computers.lab8;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gifts")
public class Gift {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String message;
    private int weight;
    private String objectType;
    private boolean wrapped;

    public Gift(String message, int weight, String objectType, boolean wrapped) {
        this.message = message;
        this.weight = weight;
        this.objectType = objectType;
        this.wrapped = wrapped;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public String getObjectType() { return objectType; }
    public void setObjectType(String objectType) { this.objectType = objectType; }

    public boolean isWrapped() { return wrapped; }
    public void setWrapped(boolean wrapped) { this.wrapped = wrapped; }
}