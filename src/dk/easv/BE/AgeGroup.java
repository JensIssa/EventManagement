package dk.easv.BE;

public enum AgeGroup {
    YOUNGERKID(1),
    OLDKID(2),
    ADULT(3);
    private final int i;
    AgeGroup(int i) {
        this.i = i;
    }
    public int getI() {
        return i;
    }
}
