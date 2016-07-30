package com.federal.fucksort.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.federal.fucksort.R;

import java.util.List;

/**
 * Created by Moosa moosa.bh@gmail.com on 7/13/2016 13 July.
 * Everything is possible in programming.
 */
public class IntAdaptor extends RecyclerView.Adapter<IntAdaptor.MyViewHolder> {

    private List<Integer> moviesList;
    private Context context;

    public IntAdaptor(Context context, List<Integer> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String value = moviesList.get(position) + "";
        holder.title.setText(value);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.value);

        }
    }
}