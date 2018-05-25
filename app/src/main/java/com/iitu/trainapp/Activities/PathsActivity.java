package com.iitu.trainapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.iitu.trainapp.Cards.CardsAdapter;
import com.iitu.trainapp.Cards.Path;
import com.iitu.trainapp.Cards.RecyclerItemClickListener;
import com.iitu.trainapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathsActivity extends BaseActivity {

    private static List<Path> paths;
    private static RecyclerView rv;
    private static CardsAdapter adapter;
    private static final String TAG = "PathsActivity";
    private static int chosenPathPosition = -1;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paths);
        db = FirebaseFirestore.getInstance();
        Log.d(TAG, "db connected");

        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(PathsActivity.this);
        rv.setLayoutManager(llm);
//        Log.d(TAG, "start initializeData");
//        initializeData("Petropavlovsk-Pavlodar",1289);
//        initializeData("Karaganda-Taldykorgan",2718);
//        initializeData("Aktau-Semei",1234);
//        initializeData("Karaganda-Omsk",2661);
//        initializeData("Kostanai-Almaty",1091);
//        initializeData("Almaty-Shu",1111);
//        Log.d(TAG, "end initializeData");
        Log.d(TAG, "db getting basic data");
        getDbPathsBasics();
        Log.d(TAG, "db got basic data" );

        adapter = new CardsAdapter(paths);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(PathsActivity.this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Path chosenPath = paths.get(position);
                        Log.d(TAG, "\titem clicked:\tposition = " + position + ", name = " + chosenPath.name + ", length=" + chosenPath.length);
                        chosenPathPosition = position;
                        Intent myIntent = new Intent(PathsActivity.this, PathMenuActivity.class);
                        PathsActivity.this.startActivity(myIntent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Path chosenPath = paths.get(position);
                        Log.d(TAG, "\titem long clicked:\t" + position + "\t" + chosenPath.name + "\t" + chosenPath.length);
                    }
                })
        );

    }

    public static Path getPath(int position){
        return paths.get(chosenPathPosition);
    }

    public static int getChosenPathPosition(){
        return chosenPathPosition;
    }

    private void getDbPathsBasics(){
        paths = new ArrayList<>();
        showProgressDialog();
        db.collection("paths_basics")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String pathId = (String) document.getData().get("path_id");
                                String pathName = (String) document.getData().get("path_name");
                                Long pathLength = (Long) document.getData().get("path_length");
                                Boolean isValid = (Boolean) document.getData().get("is_valid");
                                Path path = new Path(pathId,pathName,pathLength,isValid);
                                paths.add(path);
                            }
                            adapter.notifyDataSetChanged();
                            hideProgressDialog();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    private void addPath(final Path path){
        // Add a new document with a generated ID
        HashMap verticalDataMap = new HashMap();
        verticalDataMap.put("vertical_data",path.vertical_data);
        db.collection("paths_vertical_data")
                .add(verticalDataMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Paths\tDocument added with ID: " + documentReference.getId());
                        addPathBasicsDoc(path, documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Paths\tError adding document", e);
                    }
                });
    }
    private void addPathBasicsDoc(Path path, String pathId){
        HashMap mapOfBasics = new HashMap();
        mapOfBasics.put("path_id", pathId);
        mapOfBasics.put("path_name", path.name);
        mapOfBasics.put("path_length", path.length);
        mapOfBasics.put("is_valid", path.is_valid);
        db.collection("paths_basics")
                .add(mapOfBasics)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Paths_basics\tDocument added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Paths_basics\tError adding document", e);
                    }
                });
    }
    // This method creates an ArrayList that has three Path objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.
    private void initializeData(String name, int length){
        Path path = new Path("nothing yet",name, length, true);
        ArrayList<Double> verticalData = new ArrayList();
        double val = 0, A=1, step = 0.1;
        for(int i=0; i<length; i++){
            val=A*Math.sin(step);
            verticalData.add(val);
            if(i%7==0){
                A+=0.01;
            }
            step+=0.1;
        }
        path.setDataFromDb(verticalData);
        addPath(path);
    }
}