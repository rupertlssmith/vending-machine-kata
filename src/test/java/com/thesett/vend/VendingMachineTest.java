package com.thesett.vend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link VendingMachine}
 */
public class VendingMachineTest {

    VendingMachine machine;

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

    public void insertingMoneyIncreasesBalance() throws Exception {
        machine.insertMoney(Coin.Ten);
        machine.getBalance();
    }
}