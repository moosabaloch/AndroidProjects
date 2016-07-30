package com.federal.fucksort.Algo;

import java.util.ArrayList;

/**
 * Created by Moosa moosa.bh@gmail.com on 7/13/2016 13 July.
 * Everything is possible in programming.
 */
public class BubbleSort {
    public int pos = 0;

    public synchronized String bubbleSort(ArrayList<Integer> num) {
        StringBuilder builder = new StringBuilder();
        int j;
        boolean flag = true;   // set flag to true to begin first pass
        int temp;   //holding variable
        builder.append("\nGiven Array ").append(num.toString());
        builder.append("\n\nStart from the starting two Values ").append(num.get(0)).append(" and ").append(num.get(1));
        while (flag) {
            flag = false;    //set flag to false awaiting a possible swap
//            System.out.println("Step "+pos+" "+Arrays.toString(num));
            for (j = 0; j < num.size() - 1; j++) {
                builder.append("\n\nStep ").append(pos).append(" ").append(num.toString());
                if (num.get(j) > num.get(j + 1))   // change to > for ascending sort
                {
                    builder.append("\nNum ").append(num.get(j)).append(" replaced by ").append(num.get(j + 1));
                    temp = num.get(j);
                    //swap elements
                    num.set(j, num.get(j + 1));
                    num.set(j + 1, temp);
                    flag = true;              //shows a swap occurred
                }
                pos++;

            }
        }
        builder.append("\n\nFinal result is : ").append(num.toString());
        return builder.toString();
    }
}
