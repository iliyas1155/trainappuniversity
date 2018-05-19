package com.example.iitu.trainapp.Cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iitu.trainapp.R;

import java.util.List;

public class VagonsCardsAdapterNotSettable extends RecyclerView.Adapter<VagonsCardsAdapterNotSettable.VagonViewHolderNotSettable>{

    public List<Vagon> vagons;

    public VagonsCardsAdapterNotSettable(List<Vagon> vagons){
        this.vagons = vagons;
    }
    @Override
    public VagonViewHolderNotSettable onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vagon_item_not_settable, viewGroup, false);
        VagonViewHolderNotSettable vvh = new VagonViewHolderNotSettable(v);
        return vvh;
    }

    @Override
    public void onBindViewHolder(VagonViewHolderNotSettable vagonVHolder, int i) {
        String massStr;
        Double mass = vagons.get(i).mass;
        if(mass%1==0d){
            massStr = Math.round(mass)+"";
        }else {
            massStr = mass == null ? "" : (mass + "");
        }
        String idStr = vagons.get(i).id+"";
        vagonVHolder.vagonMass.setText(massStr);
        vagonVHolder.vagonId.setText(idStr);

        // update MyCustomEditTextListener every time we bind a new item
        // so that it knows what item in vagons to update
    }
    @Override
    public int getItemCount() {
        return vagons.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class VagonViewHolderNotSettable extends RecyclerView.ViewHolder {
        CardView vagonCv;
        TextView vagonId;
        TextView vagonMass;
        VagonViewHolderNotSettable(View itemView) {
            super(itemView);

            vagonCv = itemView.findViewById(R.id.vagon_cv);
            vagonMass = itemView.findViewById(R.id.mass_of_vagon);
            vagonId = itemView.findViewById(R.id.vagon_id);
        }
    }
}