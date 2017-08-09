package com.thesett.vend;

import java.util.*;

/**
 * Encapsulates the state of a vending machine and the operations that can be performed on it
 */
public class VendingMachine {
    private enum State {
        On,
        Off;
    }

    private State state = State.Off;

    private List<Coin> insertedCoins = new LinkedList<Coin>();

    private Map<Coin, Integer> availableChange = new HashMap<Coin, Integer>();

    private Map<Item, Integer> availableItems = new HashMap<Item, Integer>();

    private int balance = 0;

    public VendingMachine() {
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

    public int getBalance() {
        return balance;
    }

    public List<Coin> coinReturn() throws MachineIsOffException {
        if (!isOn()) {
            throw new MachineIsOffException();
        }

        return Collections.unmodifiableList(insertedCoins);
    }

    public void restockItem(Item item, int numItemsToAdd) throws MachineIsOffException {
        if (!isOn()) {
            throw new MachineIsOffException();
        }

        int oldStockCount = availableItems.getOrDefault(item, 0);
        availableItems.put(item, oldStockCount + numItemsToAdd);
    }

    public int getStockCount(Item item) {
        return availableItems.getOrDefault(item, 0);
    }

    public void vendItem(Item item) throws MachineIsOffException, InsufficientStockException, InsufficientBalanceException {
        if (!isOn()) {
            throw new MachineIsOffException();
        }

        if (getStockCount(item) < 1) {
            throw new InsufficientStockException();
        }

        if (balance < item.getPenceValue()) {
            throw new InsufficientBalanceException();
        }
    }
}