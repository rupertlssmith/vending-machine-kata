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

        insertedCoins.add(coin);
    }

    public int getUsersBalance() {
        int total = 0;

        for (Coin coin : insertedCoins) {
            total += coin.getPenceValue();
        }

        return total;
    }

    public List<Coin> coinReturn() throws MachineIsOffException {
        if (!isOn()) {
            throw new MachineIsOffException();
        }

        List<Coin> result =  new LinkedList<Coin>(insertedCoins);

        insertedCoins.clear();

        return Collections.unmodifiableList(result);
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

        if (getUsersBalance() < item.getPenceValue()) {
            throw new InsufficientBalanceException();
        }
    }
}