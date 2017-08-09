package com.thesett.vend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

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

        assertEquals(0, machine.getBalance());
    }

    @Test
    public void insertingMoneyIncreasesBalance() throws Exception {
        machine.setOn();

        for (int i = 0; i < 1000; i++) {
            int oldBalance = machine.getBalance();

            Coin randomCoin = getRandomCoin();
            machine.insertMoney(randomCoin);

            int newBalance = machine.getBalance();

            assertEquals(oldBalance + randomCoin.getPenceValue(), newBalance);
        }
    }

    public Coin getRandomCoin() {
        Coin[] coins = Coin.values();
        int index = random.nextInt(coins.length);
        return coins[index];
    }
}