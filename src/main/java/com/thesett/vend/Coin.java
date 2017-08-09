package com.thesett.vend;

public enum Coin implements PenceValued {
    Ten(10),
    Twenty(20),
    Fifty(50),
    Pound(100);

    private final int pence;

    Coin(int pence) {
        this.pence=pence;
    }

    public int getPenceValue() {
        return pence;
    }
}
