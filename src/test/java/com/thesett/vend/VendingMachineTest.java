package com.thesett.vend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * Unit tests for {@link VendingMachine}
 */
public class VendingMachineTest {

    VendingMachine machine;

    Random random = new Random();

    @Before
    public void setup() {
        machine = new VendingMachine();
    }

    @Test
    public void defaultStateIsOff() {
        assertFalse(machine.isOn());
    }

    @Test
    public void turnsOn() {
        machine.setOn();
        assertTrue(machine.isOn());
    }

    @Test
    public void turnsOffFromOn() {
        machine.setOn();
        assertTrue(machine.isOn());

        machine.setOff();
        assertFalse(machine.isOn());
    }

    @Test
    public void turnOnWhilstAlreadyOnRemainsOn() {
        machine.setOn();
        assertTrue(machine.isOn());

        machine.setOn();
        assertTrue(machine.isOn());
    }

    @Test
    public void turnOffWhilstAlreadyOffRemainsOff() {
        assertFalse(machine.isOn());

        machine.setOff();
        assertFalse(machine.isOn());
    }

    @Test
    public void canInsertMoneyWhilstOn() throws Exception {
        machine.setOn();

        machine.insertMoney(Coin.Ten);
        machine.insertMoney(Coin.Twenty);
        machine.insertMoney(Coin.Fifty);
        machine.insertMoney(Coin.Pound);
    }

    @Test(expected = MachineIsOffException.class)
    public void insertingMoneyWhilstOffFails() throws Exception {
        machine.insertMoney(Coin.Ten);
    }

    @Test
    public void initialBalanceIsZeroAfterTurningOn() throws Exception {
        machine.setOn();

        assertEquals(0, machine.getUsersBalance());
    }

    @Test
    public void insertingMoneyIncreasesBalanceCorrectly() throws Exception {
        machine.setOn();

        for (int i = 0; i < 1000; i++) {
            int oldBalance = machine.getUsersBalance();

            Coin randomCoin = getRandomCoin();
            machine.insertMoney(randomCoin);

            int newBalance = machine.getUsersBalance();

            assertEquals(oldBalance + randomCoin.getPenceValue(), newBalance);
        }
    }

    @Test(expected = MachineIsOffException.class)
    public void cannotReturnCoinsWhilstOff() throws Exception {
        machine.coinReturn();
    }

    @Test
    public void allInsertedCoinsAreReturned() throws Exception {
        machine.setOn();

        int[] insertedCoins = new int[Coin.values().length];

        for (int i = 0; i < 1000; i++) {
            Coin randomCoin = getRandomCoin();
            machine.insertMoney(randomCoin);

            int index = randomCoin.ordinal();

            insertedCoins[index] = insertedCoins[index]++;
        }

        List<Coin> returnedCoins = machine.coinReturn();

        for (Coin coin : returnedCoins) {
            int index = coin.ordinal();

            insertedCoins[index] = insertedCoins[index]--;
        }

        for (int i = 0; i < Coin.values().length; i++) {
            assertEquals(0, insertedCoins[i]);
        }
    }

    @Test
    public void balanceIsZeroAfterReturningCoins() throws Exception {
        machine.setOn();

        for (int i = 0; i < 1000; i++) {
            Coin randomCoin = getRandomCoin();
            machine.insertMoney(randomCoin);
        }

        machine.coinReturn();

        assertEquals(0, machine.getUsersBalance());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cannotModifyReturnedCoinList() throws Exception {
        machine.setOn();
        machine.insertMoney(Coin.Ten);
        List<Coin> returnedCoins = machine.coinReturn();

        returnedCoins.remove(0);
    }

    @Test(expected = MachineIsOffException.class)
    public void cannotRestockWhilstOff() throws Exception {
        machine.restockItem(Item.A, 100);
    }

    @Test
    public void restockingItemsIncreasesStockCorrectly() throws Exception {
        machine.setOn();

        for (int i = 0; i < 1000; i++) {
            Item item = getRandomItem();
            int oldStockCount = machine.getStockCount(item);

            int numToRestock = random.nextInt(10) + 1;
            machine.restockItem(item, numToRestock);

            int newStockCount = machine.getStockCount(item);

            assertEquals(oldStockCount + numToRestock, newStockCount);
        }
    }

    @Test(expected = MachineIsOffException.class)
    public void cannotVendWhilstOff() throws Exception {
        machine.vendItem(Item.A);
    }

    @Test(expected = InsufficientStockException.class)
    public void cannotVendItemOutOfStock() throws Exception {
        machine.setOn();

        assertEquals(0, machine.getStockCount(Item.A));
        machine.vendItem(Item.A);
    }

    @Test(expected = InsufficientBalanceException.class)
    public void cannotVendItemWithoutSufficientBalance() throws Exception {
        machine.setOn();
        machine.restockItem(Item.A, 10);
        machine.insertMoney(Coin.Ten);

        machine.vendItem(Item.A);
    }

    // Check balance after vending is correct.

    public Coin getRandomCoin() {
        Coin[] coins = Coin.values();
        int index = random.nextInt(coins.length);
        return coins[index];
    }

    public Item getRandomItem() {
        Item[] items = Item.values();
        int index = random.nextInt(items.length);
        return items[index];
    }
}