package com.iitu.trainapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.iitu.trainapp.Cards.Path;
import com.iitu.trainapp.R;

public class PathInfoActivity extends BaseActivity {

    private Path path;
    private TextView pathNameTv;
    private TextView pathLengthTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_info);
        path = PathsActivity.getPath(PathsActivity.getChosenPathPosition());

        pathNameTv = findViewById(R.id.path_info_name);
        pathLengthTv = findViewById(R.id.path_info_length);

        pathNameTv.setText(path.name);
        pathLengthTv.setText(getString(R.string.path_info_length_value, path.length));
    }
}
