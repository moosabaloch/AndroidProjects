package com.federal.fucksort.Algo;

import java.util.ArrayList;

/**
 * Created by Moosa moosa.bh@gmail.com on 7/14/2016 14 July.
 * Everything is possible in programming.
 */
public class InsertionSort {
    public synchronized String sort(ArrayList<Integer> input) {
        StringBuilder builder = new StringBuilder();
        int temp;
        for (int i = 1; i < input.size(); i++) {
            builder.append("\n\nStep ").append(i - 1).append(": ").append(input.toString());
            for (int j = i; j > 0; j--) {
                builder.append("\n\n").append(input.get(j)).append(" is comparing by ").append(input.get(j - 1));
                if (input.get(j) < input.get(j - 1)) {
                    builder.append("\n").append(input.get(j)).append(" IS REPLACED BY ").append(input.get(j - 1));

                    temp = input.get(j);
                    input.set(j, input.get(j - 1));
                    input.set(j - 1, temp);
                }
            }
        }
        builder.append("\n\nFinal result is : ").append(input.toString());
        return builder.toString();
    }

}
