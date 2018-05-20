package com.example.iitu.trainapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.iitu.trainapp.Cards.Vagon;
import com.example.iitu.trainapp.Cards.VagonsCardsAdapter;
import com.example.iitu.trainapp.Cards.VagonsCardsAdapterNotSettable;
import com.example.iitu.trainapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SequensesCustomActivity extends BaseActivity {
    private static List<Vagon> vagons;
    private static List<Vagon> vagonsOptimized;
    private static RecyclerView vagonsRv;
    private static RecyclerView vagonsOptimizedRv;
    EditText numberOfVagonsET;
    Button optimizeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequenses_custom);

        numberOfVagonsET = findViewById(R.id.amount_of_vagons);
        vagonsRv = findViewById(R.id.rv_vagons_settable);
        vagonsOptimizedRv = findViewById(R.id.rv_vagons_optimized);
        optimizeButton = findViewById(R.id.optimize_sequence_button);

        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        vagonsRv.setLayoutManager(llm1);
        LinearLayoutManager llm2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        vagonsOptimizedRv.setLayoutManager(llm2);

        vagons = new ArrayList();

        NumberOfVagonsEditTextListener numberOfVagonsEditTextListener = new NumberOfVagonsEditTextListener();//here creates List Of Vagons
        numberOfVagonsET.addTextChangedListener(numberOfVagonsEditTextListener);

        optimizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vagonsOptimized = createOptimizedSequense();
                initializeOptimizedVagonsRecyclerView();
            }
        });
    }

    private List<Vagon> createOptimizedSequense() {
        TreeMap<Double/*mass of vagon*/, List<Vagon>> sortedVagonsMap = new TreeMap();//TreeMap sorts Vagons in ascending order
        for(Vagon v : vagons){
            Double mass = v.mass;
            if(mass == null){
                mass = 0d;
            }
            if(sortedVagonsMap.containsKey(mass)==false) {
                sortedVagonsMap.put(mass, new ArrayList<Vagon>());
            }
            sortedVagonsMap.get(mass).add(v);
        }
        List<Vagon> vagonsOptimized = new ArrayList();
        for(List<Vagon> vagonsList : sortedVagonsMap.values()){//sorted in Ascending order
            vagonsOptimized.addAll(vagonsList);
        }
        return vagonsOptimized;
    }

    private void initializeVagonsRecyclerView(int numberOfVagons) {
        vagons = new ArrayList();
        for(int i=0; i<numberOfVagons; i++){
            vagons.add(new Vagon(i+1));
        }
        VagonsCardsAdapter adapter = new VagonsCardsAdapter(vagons);
        vagonsRv.setAdapter(adapter);
        vagonsRv.setVisibility(View.VISIBLE);
        optimizeButton.setVisibility(View.VISIBLE);
    }

    private class NumberOfVagonsEditTextListener implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if(charSequence.length()!=0){
                initializeVagonsRecyclerView(Integer.parseInt(charSequence.toString()));//when user inputs number of vagons, we create new list of vagons
            }
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    private void initializeOptimizedVagonsRecyclerView() {
        VagonsCardsAdapterNotSettable adapter = new VagonsCardsAdapterNotSettable(vagonsOptimized, true);
        vagonsOptimizedRv.setAdapter(adapter);
        vagonsOptimizedRv.setVisibility(View.VISIBLE);
    }
}
