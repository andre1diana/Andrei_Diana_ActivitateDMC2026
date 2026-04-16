package mta.computers.lab8;

public class Gift {
    private String message;
    private int weight;
    private Objects objectType;
    private boolean wrapped;

    public Gift(String message, int weight, Objects objectType, boolean wrapped) {
        this.message = message;
        this.weight = weight;
        this.objectType = objectType;
        this.wrapped = wrapped;
    }

    public String getMessage() {
        return message;
    }

    public int getWeight() {
        return weight;
    }

    public Objects getObjectType() {
        return objectType;
    }

    public boolean isWrapped() {
        return wrapped;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setObjectType(Objects objectType) {
        this.objectType = objectType;
    }

    public void setWrapped(boolean wrapped) {
        this.wrapped = this.wrapped;
    }


}
