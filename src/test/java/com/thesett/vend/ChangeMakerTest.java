package com.thesett.vend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for {@link ChangeMaker}
 */
public class ChangeMakerTest {
    @Test(expected = InsufficientChangeException.class)
    public void cannotMakeChangeFromNoCoins() throws Exception {
        ChangeMaker changeMaker = new ChangeMaker(new HashMap<Coin, Integer>());
        changeMaker.makeChange(20);
    }

    @Test
    public void changeForZeroIsAlwaysEmptyList() throws Exception {
        ChangeMaker changeMaker = new ChangeMaker(new HashMap<Coin, Integer>());
        changeMaker.makeChange(0);
    }

    @Test
    public void changeForSingleCoinsIsTheCoinItself() throws Exception {
        for (Coin coin : Coin.values()) {
            Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
            coins.put(coin, 1);

            ChangeMaker changeMaker = new ChangeMaker(coins);
            List<Coin> change = changeMaker.makeChange(coin.getPenceValue());

            assertEquals(coin.getPenceValue(), valueOf(change));
        }
    }

    @Test
    public void stackOfSingleCoinDenominationMakesChangeForItsTotalValue() throws Exception {
        int stackSize = 10;

        for (Coin coin : Coin.values()) {
            Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
            coins.put(coin, stackSize);

            ChangeMaker changeMaker = new ChangeMaker(coins);
            List<Coin> change = changeMaker.makeChange(coin.getPenceValue() * stackSize);

            assertEquals(coin.getPenceValue() * stackSize, valueOf(change));
        }
    }

    @Test(expected = InsufficientChangeException.class)
    public void cannotMakeChangeFor15OutOf10s() throws Exception {
        tryBadCombination(Coin.Ten, 15);
    }

    @Test(expected = InsufficientChangeException.class)
    public void cannotMakeChangeFor30OutOf20s() throws Exception {
        tryBadCombination(Coin.Twenty, 30);
    }

    @Test(expected = InsufficientChangeException.class)
    public void cannotMakeChangeFor80OutOf50s() throws Exception {
        tryBadCombination(Coin.Fifty, 80);
    }

    @Test(expected = InsufficientChangeException.class)
    public void cannotMakeChangeFor150OutOf100s() throws Exception {
        tryBadCombination(Coin.Pound, 150);
    }

    @Test
    public void canMakeChangeFor80OutOf50sAnd20sByBacktracking() throws Exception {
        Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
        coins.put(Coin.Fifty, 100);
        coins.put(Coin.Twenty, 100);

        ChangeMaker changeMaker = new ChangeMaker(coins);
        changeMaker.makeChange(80);
    }

    @Test
    public void canMakeChangeFor180OutOf100sAnd50sAnd20sByBacktracking() throws Exception {
        Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
        coins.put(Coin.Pound, 100);
        coins.put(Coin.Fifty, 100);
        coins.put(Coin.Twenty, 100);

        ChangeMaker changeMaker = new ChangeMaker(coins);
        changeMaker.makeChange(180);
    }

    private void tryBadCombination(Coin ten, int valueToChange) throws InsufficientChangeException {
        Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
        coins.put(ten, 100);

        ChangeMaker changeMaker = new ChangeMaker(coins);
        changeMaker.makeChange(valueToChange);
    }

    public void canNeverMakeChangeForValueHigherThanAvailableCoins() {}

    private int valueOf(List<Coin> coins) {
        int total = 0;

        for (Coin coin : coins) {
            total += coin.getPenceValue();
        }

        return total;
    }
}
