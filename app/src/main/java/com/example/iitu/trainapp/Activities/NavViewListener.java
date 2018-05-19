package com.example.iitu.trainapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.example.iitu.trainapp.R;

public class NavViewListener implements NavigationView.OnNavigationItemSelectedListener{
    Context context;
    DrawerLayout drawer;
    public NavViewListener(Context context, DrawerLayout drawer){
        this.context = context;
        this.drawer = drawer;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent myIntent = new Intent(context, MainMenuActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_paths) {
            Intent myIntent = new Intent(context, PathsActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_formulas) {
            Intent myIntent = new Intent(context, FormulasActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_about) {
            Intent myIntent = new Intent(context, AboutActivity.class);
            context.startActivity(myIntent);
        }
        if (id == R.id.nav_settings) {
            Intent myIntent = new Intent(context, SettingsActivity.class);
            context.startActivity(myIntent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
