package com.federal.fucksort;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.federal.fucksort.adaptor.IntAdaptor;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertArrayFragment extends Fragment implements View.OnClickListener {
    protected Button add, sort, remove;
    protected EditText text;
    private RecyclerView listView;
    private ArrayList<Integer> myArray = new ArrayList<>();
    private IntAdaptor adaptor;

    public InsertArrayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_array, container, false);
        add = (Button) view.findViewById(R.id.insertArrayAddButton);
        remove = (Button) view.findViewById(R.id.insertArrayRemoveButton);
        sort = (Button) view.findViewById(R.id.insertArraySortButton);
        listView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listView.setLayoutManager(mLayoutManager);
        text = (EditText) view.findViewById(R.id.insertArrayEditText);
        sort.setOnClickListener(this);
        add.setOnClickListener(this);
        remove.setOnClickListener(this);
        adaptor = new IntAdaptor(getActivity(), myArray);
        listView.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insertArrayAddButton:
                if (text.getText().length() >= 1) {
                    int val = Integer.parseInt(text.getText().toString());
                    myArray.add(val);
                    text.setText("");
                    adaptor.notifyDataSetChanged();
                }
                break;
            case R.id.insertArrayRemoveButton:
                if (myArray.size() >= 1) {
                    myArray.remove(myArray.size() - 1);
                    adaptor.notifyDataSetChanged();
                }
                break;
            case R.id.insertArraySortButton:
                if (myArray.size() > 1) {
                    showDialog();
                }
                break;
            default:
                break;
        }
    }

    public void showDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setTitle("Select Algorithm:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Shell Sort");
        arrayAdapter.add("Selection Sort");
        arrayAdapter.add("Bubble Sort");
        arrayAdapter.add("Insertion Sort");

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShellSortFragment fragment = new ShellSortFragment();
                        Bundle bundle = new Bundle();
                        ArrayList<Integer> newArray = new ArrayList<Integer>();
                        newArray.addAll(myArray);
                        bundle.putIntegerArrayList("array", newArray);
                        bundle.putInt("algo", which);
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().add(R.id.main, fragment).addToBackStack("").commit();

                    }
                });
        builderSingle.show();
    }
}
