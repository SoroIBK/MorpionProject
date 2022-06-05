package soro.aka.morpion;

public enum Owner {
    NONE, FIRST, SECOND;

    public Owner opposite() {
        if (this == SECOND) return FIRST;
        if (this == FIRST) return SECOND;
        return NONE;
    }
}
