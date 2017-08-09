package com.thesett.vend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link VendingMachine}
 */
public class VendingMachineTest {

    @Test
    public void defaultStateIsOff() {
        VendingMachine machine = new VendingMachine();
        assertFalse(machine.isOn());
    }

    @Test
    public void turnsOn() {
        VendingMachine machine = new VendingMachine();
        machine.setOn();
        assertTrue(machine.isOn());
    }

    @Test
    public void turnsOffFromOn() {
        VendingMachine machine = new VendingMachine();

        machine.setOn();
        assertTrue(machine.isOn());

        machine.setOff();
        assertFalse(machine.isOn());
    }

    @Test
    public void turnOnWhilstAlreadyOnRemainsOn() {
        VendingMachine machine = new VendingMachine();

        machine.setOn();
        assertTrue(machine.isOn());

        machine.setOn();
        assertTrue(machine.isOn());
    }

    @Test
    public void turnOffWhilstAlreadyOffRemainsOff() {
        VendingMachine machine = new VendingMachine();
        assertFalse(machine.isOn());

        machine.setOff();
        assertFalse(machine.isOn());
    }


}