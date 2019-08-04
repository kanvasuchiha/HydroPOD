package com.smartherd.hydropod;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;

import android.view.MenuItem;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class home_login extends AppCompatActivity implements MasterPasswordDialog.ExampleDialogListener {

    private final String MASTER_PASSWORD = "QdQ0KexVijtgdbOo6lCgeauTOh5gm8fnvc9a0USx88Z2KFuPIYe9P0CxL0NrzI52W0Mami6qsW8yAgfan1ivzBnvfshLCusxsOIiyQUIp0deuc3DNt75L5TB";
    private final String abhi = "abhi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login_admin) {
            openDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openDialog() {

        MasterPasswordDialog masterPasswordDialog = new MasterPasswordDialog();
        masterPasswordDialog.show(getSupportFragmentManager(), "example dialog");
    }


    @Override
    public void checkMasterPassword(String password) {
        if(password.equals(abhi))
        {
            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
        }
        else{
            return;
        }

    }
}

