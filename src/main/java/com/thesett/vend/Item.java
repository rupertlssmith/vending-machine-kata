package com.thesett.vend;

public enum Item {
    A(60),
    B(100),
    C(170);

    private final int pence;

    Item(int pence) {
        this.pence = pence;
    }

    public int getPenceValue() {
        return pence;
    }
}
