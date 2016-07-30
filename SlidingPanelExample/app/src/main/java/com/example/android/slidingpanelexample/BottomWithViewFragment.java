package com.example.android.slidingpanelexample;

import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;

/**
 * A placeholder fragment containing a simple view.
 */
public class BottomWithViewFragment extends Fragment {

    public BottomWithViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_bottom_with_view, container, false);
        final BottomSheetLayout bottomSheet = (BottomSheetLayout) view.findViewById(R.id.bottomsheet);
        final TextView textView=new TextView(getActivity());
        textView.setText("Boooty Sheet");
        textView.setTextSize(34);
        ((Button)view.findViewById(R.id.bootysheetopener)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.showWithSheetView(LayoutInflater.from(getActivity()).inflate(R.layout.template, bottomSheet, false));
            }
        });


      /*  FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomSheet.showWithSheetView(textView);

                            }
                        }).show();
            }
        });
*/


        return  view;
    }
}
