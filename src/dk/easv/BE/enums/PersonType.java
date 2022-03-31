package dk.easv.BE.enums;

public enum PersonType {
    USER(1),
    EVENTMANAGER(2),
    ADMIN(3);

    private final int i;
    PersonType(int i) {
        this.i = i;
    }
    public int getI() {
        return i;
    }

}
