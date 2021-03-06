package com.thesett.vend;

import com.thesett.vend.change.ChangeMaker;

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

    /**
     * Note that the machines balance is the committed balance of the machines coins after a transaction
     * has completed. So if the machine has 1 pound in it and the user has put in 50p, the machines balance will
     * be 1 pound, until the user completes a purchase or 50p. Only after the purchase will the users balance transfer
     * to the machine.
     *
     * @return The machines committed balance.
     */
    public int getMachinesBalance() {
        int total = 0;

        for (Coin coin : Coin.values()) {
            total += availableChange.getOrDefault(coin, 0) * coin.getPenceValue();
        }

        return total;
    }

    public List<Coin> coinReturn() throws MachineIsOffException {
        if (!isOn()) {
            throw new MachineIsOffException();
        }

        List<Coin> result = new LinkedList<Coin>(insertedCoins);

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

    public void vendItem(Item item)
            throws MachineIsOffException, OutOfStock, InsufficientMoneyException, CannotMakeChangeException {
        if (!isOn()) {
            throw new MachineIsOffException();
        }

        if (getStockCount(item) < 1) {
            throw new OutOfStock();
        }

        int usersBalance = getUsersBalance();

        if (usersBalance < item.getPenceValue()) {
            throw new InsufficientMoneyException();
        }

        ChangeMaker changeMaker = new ChangeMaker(getAllAvailableCoins());
        List<Coin> change = changeMaker.makeChange(usersBalance - item.getPenceValue() );

        availableChange = changeMaker.changeRemaining();
    }

    private Map<Coin, Integer> getAllAvailableCoins() {
        Map<Coin, Integer> result = new HashMap<Coin, Integer>(availableChange);

        for (Coin coin : insertedCoins) {
            int coinCount = result.getOrDefault(coin, 0);
            result.put(coin, coinCount + 1);
        }

        return result;
    }
}