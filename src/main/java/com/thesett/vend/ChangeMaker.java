package com.thesett.vend;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChangeMaker {
    private final Map<Coin, Integer> availableChange;

    public ChangeMaker(Map<Coin, Integer> availableChange) {
        this.availableChange = new HashMap<Coin, Integer>(availableChange);
    }

    public List<Coin> makeChange(int valueToChange) throws InsufficientChangeException {
        List<Coin> result = makeChangeInner(valueToChange);

        if (result == null) {
            throw new InsufficientChangeException();
        }

        return result;
    }

    public Map<Coin, Integer> changeRemaining() {
        throw new IllegalStateException();
    }

    /**
     * Implements a recursive change-making algorithm.
     * <p>
     * Note: Using <tt>null</tt> as a return value to indicate failure to find a solution is less than ideal.
     * The alternatives would be to use an exception for flow-control, which is also less than ideal, or to return
     * the list of coins and an indication of whether a solution was found as a pair, which is a little inconvenient
     * in Java.
     *
     * @return A list of coins making change when a solution can be found. If the value of change to be
     * made is zero, and empty list will be returned. If no solution can be found, <tt>null</tt>
     * will be returned to indicate this special case.
     */
    private List<Coin> makeChangeInner(int valueToChange) throws InsufficientChangeException {
        if (valueToChange == 0)
            return new LinkedList<Coin>();

        for (int i = Coin.values().length - 1; i >= 0; i--) {
            Coin coin = Coin.values()[i];

            if (availableChange.getOrDefault(coin, 0) > 0) {
                if (coin.getPenceValue() <= valueToChange) {
                    List<Coin> result = new LinkedList<Coin>();
                    result.add(coin);

                    int remainder = valueToChange - coin.getPenceValue();

                    if (remainder == 0)
                        return result;
                    else {
                        List<Coin> remainingChange = makeChangeInner(remainder);

                        if (remainingChange != null) {
                            result.addAll(remainingChange);
                            return result;
                        }
                    }
                }
            }
        }

        return null;
    }
}