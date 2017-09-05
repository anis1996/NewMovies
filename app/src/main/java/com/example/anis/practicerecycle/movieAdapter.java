package com.example.anis.practicerecycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by anis on 9/4/2017.
 */

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.movieViewHolder> {
    private LayoutInflater LF;

    public movieAdapter(Context c)
    {
        LF = LayoutInflater.from(c);
    }


    @Override
    public movieAdapter.movieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LF.inflate(R.layout.recyclelist, parent, false);
        return new movieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(movieAdapter.movieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class movieViewHolder extends RecyclerView.ViewHolder
    {
        TextView movieItemView;

        public movieViewHolder(View view)
        {
            super(view);

            movieItemView = (TextView) view.findViewById(R.id.movieList);

        }

        void bind(int listIndex) {
            movieItemView.setText(String.valueOf(listIndex));
        }
    }

}
