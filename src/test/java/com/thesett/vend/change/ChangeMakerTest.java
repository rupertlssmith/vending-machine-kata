package com.thesett.vend.change;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import com.thesett.vend.Coin;
import com.thesett.vend.CannotMakeChangeException;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for {@link ChangeMaker}
 */
public class ChangeMakerTest {
    @Test(expected = CannotMakeChangeException.class)
    public void cannotMakeChangeFromNoCoins() throws Exception {
        ChangeMaker changeMaker = new ChangeMaker(new HashMap<Coin, Integer>());
        changeMaker.makeChange(20);
    }

    @Test
    public void changeForZeroIsAlwaysEmptyList() throws Exception {
        ChangeMaker changeMaker = new ChangeMaker(new HashMap<Coin, Integer>());
        changeMaker.makeChange(0);

        int coinsValueAfter = valueOf(changeMaker.changeRemaining());
        assertEquals(0, coinsValueAfter);
    }

    @Test
    public void changeForSingleCoinsIsTheCoinItself() throws Exception {
        for (Coin coin : Coin.values()) {
            Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
            coins.put(coin, 1);

            ChangeMaker changeMaker = new ChangeMaker(coins);
            List<Coin> change = changeMaker.makeChange(coin.getPenceValue());

            assertEquals(coin.getPenceValue(), valueOf(change));

            int coinsValueAfter = valueOf(changeMaker.changeRemaining());
            assertEquals(0, coinsValueAfter);
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

            int coinsValueAfter = valueOf(changeMaker.changeRemaining());
            assertEquals(0, coinsValueAfter);
        }
    }

    @Test(expected = CannotMakeChangeException.class)
    public void cannotMakeChangeFor15OutOf10s() throws Exception {
        tryBadCombination(Coin.Ten, 15);
    }

    @Test(expected = CannotMakeChangeException.class)
    public void cannotMakeChangeFor30OutOf20s() throws Exception {
        tryBadCombination(Coin.Twenty, 30);
    }

    @Test(expected = CannotMakeChangeException.class)
    public void cannotMakeChangeFor80OutOf50s() throws Exception {
        tryBadCombination(Coin.Fifty, 80);
    }

    @Test(expected = CannotMakeChangeException.class)
    public void cannotMakeChangeFor150OutOf100s() throws Exception {
        tryBadCombination(Coin.Pound, 150);
    }

    @Test
    public void canMakeChangeFor80OutOf50sAnd20sByBacktracking() throws Exception {
        Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
        coins.put(Coin.Fifty, 100);
        coins.put(Coin.Twenty, 100);
        int coinsValueBefore = valueOf(coins);

        ChangeMaker changeMaker = new ChangeMaker(coins);
        List<Coin> change = changeMaker.makeChange(80);
        assertEquals(80, valueOf(change));

        int coinsValueAfter = valueOf(changeMaker.changeRemaining());
        assertEquals(coinsValueBefore - 80, coinsValueAfter);
    }

    @Test
    public void canMakeChangeFor180OutOf100sAnd50sAnd20sByBacktracking() throws Exception {
        Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
        coins.put(Coin.Pound, 100);
        coins.put(Coin.Fifty, 100);
        coins.put(Coin.Twenty, 100);
        int coinsValueBefore = valueOf(coins);

        ChangeMaker changeMaker = new ChangeMaker(coins);
        List<Coin> change = changeMaker.makeChange(180);
        assertEquals(180, valueOf(change));

        int coinsValueAfter = valueOf(changeMaker.changeRemaining());
        assertEquals(coinsValueBefore - 180, coinsValueAfter);
    }


    @Test
    public void canNeverMakeChangeForValueHigherThanAvailableCoins() {
        int stackSize = 10;

        for (Coin coin : Coin.values()) {
            Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
            coins.put(coin, stackSize);

            ChangeMaker changeMaker = new ChangeMaker(coins);

            boolean testPassed = false;

            try {
                changeMaker.makeChange((coin.getPenceValue() * stackSize) + 10);
            } catch (CannotMakeChangeException e) {
                testPassed = true;
            }

            assertTrue(coin.toString(), testPassed);
        }
    }

    private void tryBadCombination(Coin ten, int valueToChange) throws CannotMakeChangeException {
        Map<Coin, Integer> coins = new HashMap<Coin, Integer>();
        coins.put(ten, 100);

        ChangeMaker changeMaker = new ChangeMaker(coins);
        changeMaker.makeChange(valueToChange);
    }

    private int valueOf(List<Coin> coins) {
        int total = 0;

        for (Coin coin : coins) {
            total += coin.getPenceValue();
        }

        return total;
    }

    private int valueOf(Map<Coin, Integer> coins) {
        int total = 0;

        for (Coin coin : Coin.values()) {
            total += coins.getOrDefault(coin, 0) * coin.getPenceValue();
        }

        return total;
    }
}
