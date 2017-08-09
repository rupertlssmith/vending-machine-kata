package com.thesett.vend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    private int valueOf(List<Coin> coins) {
        int total = 0;

        for (Coin coin : coins) {
            total += coin.getPenceValue();
        }

        return total;
    }
}
