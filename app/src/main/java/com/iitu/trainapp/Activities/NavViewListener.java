package com.iitu.trainapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.iitu.trainapp.R;

import java.util.HashMap;
import java.util.Map;

public class NavViewListener implements NavigationView.OnNavigationItemSelectedListener{
    Context context;
    DrawerLayout drawer;
    Map<Integer, Class> activities;

    public NavViewListener(Context context, DrawerLayout drawer){
        this.context = context;
        this.drawer = drawer;
        this.activities = new HashMap<>();
        init();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int selectedId = getSelectedId();

        for (Map.Entry<Integer, Class> activity : activities.entrySet()) {
            if (activity.getKey() == id && activity.getKey() != selectedId) {
                Intent startActivity = new Intent(context, activity.getValue());
                context.startActivity(startActivity);
                break;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void init() {
        activities.put(R.id.nav_home,     MainMenuActivity.class);
        activities.put(R.id.nav_paths,    PathsActivity.class);
        activities.put(R.id.nav_formulas, FormulasActivity.class);
        activities.put(R.id.nav_about,    AboutActivity.class);
        activities.put(R.id.nav_settings, SettingsActivity.class);
    }

    private int getSelectedId() {
        for (Map.Entry<Integer, Class> activity : activities.entrySet()) {
            if (activity.getValue().isInstance(context)) {
                return activity.getKey();
            }
        }
        return -1;
    }
}
