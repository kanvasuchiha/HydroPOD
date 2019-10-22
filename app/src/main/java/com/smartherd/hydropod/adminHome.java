package com.smartherd.hydropod;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

public class adminHome extends AppCompatActivity
       // implements NavigationView.OnNavigationItemSelectedListener {

{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);



    }


    @Override
    public void onBackPressed()
    {
        Toast.makeText(adminHome.this, "You cannot go back. Press logout in case you want to change accounts.", Toast.LENGTH_SHORT).show();

    }
    public void onClickLogout(View v)
    {
        Intent intent2 = new Intent(adminHome.this, AdminLogin.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent2);
    }


}

