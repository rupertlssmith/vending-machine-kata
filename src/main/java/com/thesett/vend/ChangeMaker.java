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
                        result.addAll(makeChange(remainder));
                        return result;
                    }
                }
            }
        }

        throw new InsufficientChangeException();
    }

//    public Map<Coin, Integer> changeRemaining() {
//        throw new IllegalStateException();
//    }
}
