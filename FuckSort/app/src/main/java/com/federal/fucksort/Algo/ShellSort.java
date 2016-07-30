package com.federal.fucksort.Algo;///Created by Moosa on 7/13/2016 Project Sort.///

import java.util.ArrayList;

public class ShellSort {
    public int pos = 0;
    private boolean status = false;

    private String printArray(ArrayList<Integer> curArray, String state) {
        pos++;
        //System.out.println("Sorting: Step " + pos + "  " + Arrays.toString(curArray) + " " + state);
        return "\n\nSorting: Step " + pos + "  " + curArray.toString() + " " + state + "\n\n";
    }

    public String shell(ArrayList<Integer> a) {
        StringBuilder string = new StringBuilder();

        int increment = a.size() / 2;
        while (increment > 0) {
            string.append("\n--------------------------\n\nIteration with Space: ").append(increment).append("\n\n");
//            System.out.println();
            for (int i = increment; i < a.size(); i++) {

                int j = i;
                int temp = a.get(i);
                string.append("\nComparing Value: ").append(a.get(j - increment)).append(" by ").append(temp);
                //System.out.println("\nComparing Value: " + a[j - increment] + " by " + temp);
                while (j >= increment && a.get(j - increment) > temp) {
                    string.append(printArray(a, "Array Before Replace"));
                    a.set(j, a.get(j - increment));
                    j = j - increment;

                    status = true;
                }

                a.set(j, temp);
                if (status) {
                    string.append(printArray(a, "Array After Replace"));
                    status = false;
                }
            }
            if (increment == 2) {
                increment = 1;
            } else {
                increment = (int) Math.round(Math.floor((increment / 2)));
            }
        }
        string.append("\n\nFinal Result is : ").append(a.toString());
        return string.toString();
    }
}
