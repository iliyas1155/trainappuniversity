package com.iitu.trainapp.Cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitu.trainapp.R;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder>{

    public List<Path> paths;

    public CardsAdapter(List<Path> paths){
        this.paths = paths;
    }
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CardViewHolder cvh = new CardViewHolder(v);
        return cvh;
    }
    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        cardViewHolder.pathName.setText(paths.get(i).name);
        cardViewHolder.pathLength.setText(paths.get(i).length + " km");
    }
    @Override
    public int getItemCount() {
        return paths.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView pathName;
        TextView pathLength;
        CardViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            pathName = itemView.findViewById(R.id.path_name);
            pathLength = itemView.findViewById(R.id.path_length);
        }
    }
}