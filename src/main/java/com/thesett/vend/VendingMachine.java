package com.thesett.vend;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulates the state of a vending machine and the operations that can be performed on it
 */
public class VendingMachine {
    private enum State {
        On,
        Off;
    }

    private State state = State.Off;

    private List insertedCoins = new LinkedList();

    private int balance = 0;

    public VendingMachine() {
        super();
    }

    public boolean isOn() {
        return State.On.equals(state);
    }

    public void setOn() {
        state = State.On;
    }

    public void setOff() {
        state = State.Off;
    }

    public void insertMoney(Coin coin) throws MachineIsOffException {
        if (!isOn()) {
            throw new MachineIsOffException();
        }

        balance += coin.getPenceValue();
    }

    public int getBalance() throws MachineIsOffException {
        return balance;
    }

    public List<Coin> coinReturn() throws MachineIsOffException {
        if (!isOn()) {
            throw new MachineIsOffException();
        }

        return Collections.unmodifiableList(insertedCoins);
    }

    public void vendItem(Item item) throws MachineIsOffException {
        if (!isOn()) {
            throw new MachineIsOffException();
        }
    }
}