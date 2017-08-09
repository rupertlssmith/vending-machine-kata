package com.thesett.vend;

/**
 * Encapsulates the state of a vending machine and the operations that can be performed on it
 */
public class VendingMachine {
    private enum State {
        On,
        Off;
    }

    private State state = State.Off;

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
}