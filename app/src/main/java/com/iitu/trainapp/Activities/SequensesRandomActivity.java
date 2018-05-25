package com.iitu.trainapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.iitu.trainapp.Cards.Vagon;
import com.iitu.trainapp.Cards.VagonsCardsAdapterNotSettable;
import com.iitu.trainapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SequensesRandomActivity extends BaseActivity {

    private static List<Vagon> vagons;
    private static List<Vagon> vagonsOptimized;
    private static RecyclerView vagonsRv;
    private static RecyclerView vagonsOptimizedRv;
    Button optimizeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequense_random);

        vagonsRv = findViewById(R.id.rv_vagons_random);
        vagonsOptimizedRv = findViewById(R.id.rv_vagons_optimized);
        optimizeButton = findViewById(R.id.optimize_sequence_button);

        LinearLayoutManager llm1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        vagonsRv.setLayoutManager(llm1);
        LinearLayoutManager llm2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        vagonsOptimizedRv.setLayoutManager(llm2);

        int randNum = (int) Math.round(Math.random()*10+1);
        initializeVagonsRecyclerView(randNum);

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
            vagons.add(new Vagon(i+1,Math.random()*10+1));
        }
        VagonsCardsAdapterNotSettable adapter = new VagonsCardsAdapterNotSettable(vagons, false);
        vagonsRv.setAdapter(adapter);
        vagonsRv.setVisibility(View.VISIBLE);
        optimizeButton.setVisibility(View.VISIBLE);
    }

    private void initializeOptimizedVagonsRecyclerView() {
        VagonsCardsAdapterNotSettable adapter = new VagonsCardsAdapterNotSettable(vagonsOptimized, true);
        vagonsOptimizedRv.setAdapter(adapter);
        vagonsOptimizedRv.setVisibility(View.VISIBLE);
    }

}
