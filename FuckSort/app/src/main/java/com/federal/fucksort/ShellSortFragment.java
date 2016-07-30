package com.federal.fucksort;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.federal.fucksort.Algo.BubbleSort;
import com.federal.fucksort.Algo.InsertionSort;
import com.federal.fucksort.Algo.SelectionSort;
import com.federal.fucksort.Algo.ShellSort;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShellSortFragment extends Fragment {
    ArrayList<Integer> myArray;
    private TextView text;
    private String mainText;

    public ShellSortFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shell_sort, container, false);
        myArray = getArguments().getIntegerArrayList("array");
        int algo = getArguments().getInt("algo");
        switch (algo) {
            case 0:
                mainText = new ShellSort().shell(myArray);
                //Shell
                break;
            case 1:
                //Selection
                mainText = new SelectionSort().doSelectionSort(myArray);
                break;
            case 2:
                mainText = new BubbleSort().bubbleSort(myArray);
                //Bubble
                break;
            case 3:
                mainText = new InsertionSort().sort(myArray);
                //Insertion
                break;
            case 4:
                //Quick
                break;
            default:
                break;

        }
        text = (TextView) view.findViewById(R.id.sortText);
        text.setText(mainText);
        // Log.d("Sort:", ShellSort.shell(myArray));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        text.setText("");
    }
}
