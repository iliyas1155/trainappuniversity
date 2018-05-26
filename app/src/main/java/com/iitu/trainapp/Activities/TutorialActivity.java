package com.iitu.trainapp.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.hololo.tutorial.library.Step;
import com.iitu.trainapp.R;
import com.iitu.trainapp.Utils.SharedPreferencesUtil;

public class TutorialActivity extends com.hololo.tutorial.library.TutorialActivity {
    private static final int TUTORIAL_STEPS_COUNT = 9;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String packageName = getPackageName();
        Resources resources = getResources();
        int backgroundColor = ContextCompat.getColor(this, R.color.colorPrimary);

        for (int i = 1; i <= TUTORIAL_STEPS_COUNT; i++) {
            String title = getString(resources.getIdentifier("tutorial_title_" + i, "string", packageName));
            String text = getString(resources.getIdentifier("tutorial_text_" + i, "string", packageName));
            int image = resources.getIdentifier("tutorial_step_" + i, "drawable", packageName);

            addFragment(new Step.Builder().setTitle(title)
                    .setContent(text)
                    .setBackgroundColor(backgroundColor)
                    .setDrawable(image)
                    .setSummary("")
                    .build());
        }
    }

    @Override
    public void finishTutorial() {
        SharedPreferencesUtil.setTutorialShown(this);
        Intent startMenu = new Intent(this, MainMenuActivity.class);
        startMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startMenu);
    }
}
