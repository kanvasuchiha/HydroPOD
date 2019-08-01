package com.smartherd.hydropod;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    //   TO BE COPIED TO THE BUTTON PAGE
    @Override
    public void onClick(View v) {
        openDialog();
    }

    public void openDialog(){

        MasterPasswordDialog masterPasswordDialog = new MasterPasswordDialog();
        masterPasswordDialog.show(getSupportFragmentManager(), "example dialog");

    }

    //////////////////////
}
