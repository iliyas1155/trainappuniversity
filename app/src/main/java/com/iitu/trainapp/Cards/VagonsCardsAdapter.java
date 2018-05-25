package com.iitu.trainapp.Cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.iitu.trainapp.R;

import java.util.List;

public class VagonsCardsAdapter extends RecyclerView.Adapter<VagonsCardsAdapter.VagonViewHolder>{

    public List<Vagon> vagons;

    public VagonsCardsAdapter(List<Vagon> vagons){
        this.vagons = vagons;
    }
    @Override
    public VagonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vagon_item, viewGroup, false);
        VagonViewHolder vvh = new VagonViewHolder(v, new MassEditTextListener());
        return vvh;
    }
    @Override
    public void onBindViewHolder(VagonViewHolder vagonVHolder, int i) {
        String massStr = vagons.get(i).mass==null ? "" : (vagons.get(i).mass+"");
        String idStr = vagons.get(i).id+"";
        vagonVHolder.vagonMass.setText(massStr);
        vagonVHolder.vagonId.setText(idStr);

        // update MyCustomEditTextListener every time we bind a new item
        // so that it knows what item in vagons to update
        vagonVHolder.massListener.updatePosition(i);
    }
    @Override
    public int getItemCount() {
        return vagons.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class VagonViewHolder extends RecyclerView.ViewHolder {
        CardView vagonCv;
        TextView vagonId;
        EditText vagonMass;
        MassEditTextListener massListener;
        VagonViewHolder(View itemView, MassEditTextListener massListener) {
            super(itemView);

            vagonCv = itemView.findViewById(R.id.vagon_cv);
            vagonMass = itemView.findViewById(R.id.mass_of_vagon);
            vagonId = itemView.findViewById(R.id.vagon_id);
            this.massListener = massListener;

            vagonMass.addTextChangedListener(massListener);
        }
    }

    private class MassEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if(charSequence.length()>0){
                vagons.get(position).mass = Double.parseDouble(charSequence.toString());
                Log.d("vagon mass changed",position + " now has mass " + vagons.get(position).mass);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {}
    }
}