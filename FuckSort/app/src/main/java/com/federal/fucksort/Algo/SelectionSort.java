package com.federal.fucksort.Algo;

import java.util.ArrayList;

/**
 * Created by Moosa moosa.bh@gmail.com on 7/13/2016 13 July.
 * Everything is possible in programming.
 */
public class SelectionSort {
    public  int pos = 0;


    public synchronized String doSelectionSort(ArrayList<Integer> arr) {
        StringBuilder builder = new StringBuilder();
        int i, j;
        builder.append("\n").append(arr.toString());
        for (i = 0; i < arr.size() - 1; i++) {
            int index = i;
            builder.append("\n\nStep ").append(i + 1).append(" ").append(arr.toString()).append("\n\n");

            for (j = i + 1; j < arr.size(); j++)
//                System.out.println("before Loop " + j);
                if (arr.get(j) < arr.get(index))
//                System.out.println("Item " +arr[j]);
                    index = j;
//            System.out.println("after assigning index " + j);

            int smallerNumber = arr.get(index);
            builder.append("\nSmaller number ").append(smallerNumber);
            arr.set(index, arr.get(i));

            arr.set(i, smallerNumber);
//            System.out.println("Array after sort " + arr[i]);
        }
        builder.append("\n\nFinal Result:").append(arr.toString()).append("\n\n");
        return builder.toString();
    }


}
