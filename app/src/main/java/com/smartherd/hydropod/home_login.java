package com.smartherd.hydropod;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.MenuItem;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class home_login extends AppCompatActivity implements MasterPasswordDialog.ExampleDialogListener {

//    private final String MASTER_PASSWORD = "QdQ0KexVijtgdbOo6lCgeauTOh5gm8fnvc9a0USx88Z2KFuPIYe9P0CxL0NrzI52W0Mami6qsW8yAgfan1ivzBnvfshLCusxsOIiyQUIp0deuc3DNt75L5TB";

    private final String MASTER_PASSWORD = "abcd";
    private final String abhi = "abhi";


    private TextInputLayout txt1, txt2;
    private String username, password;
    private Button button;
    public int ID_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);


        txt1=findViewById(R.id.txt1H);
        txt2=findViewById(R.id.txt2H);
        button=findViewById(R.id.btnRegisterHereLH);


        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_login.this, SignUp.class);
                intent.putExtra("ID_USER", ID_user);
                startActivity(intent);
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
        ID_user=id;


        //noinspection SimplifiableIfStatement
        if (id == R.id.login_admin) {
            openDialog();
            return true;
        }

        else
        {
            Toast.makeText(home_login.this, "Chosen role: User/Consultant", Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }




    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(home_login.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(home_login.this, SignUp.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    private void alertDisplayer(String title,String message, final boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(home_login.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent = new Intent(home_login.this, SignUp.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }



    public void openDialog() {

        MasterPasswordDialog masterPasswordDialog = new MasterPasswordDialog();
        masterPasswordDialog.show(getSupportFragmentManager(), "example dialog");
    }


    @Override
    public void checkMasterPassword(String password) {
        if(password.equals(MASTER_PASSWORD))
        {
            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "The master password you have entered is wrong", Toast.LENGTH_LONG).show();
        }

    }


    public void loginSubmit(View view)
    {
        username= txt1.getEditText().getText().toString().trim();
        password= txt2.getEditText().getText().toString().trim();
        Toast.makeText(home_login.this, username+password, Toast.LENGTH_LONG).show();
        ParseUser.logInInBackground(username, password, new LogInCallback() {
        @Override
        public void done(ParseUser parseUser, ParseException e) {
            if (parseUser != null) {
                if(parseUser.getBoolean("emailVerified")) {
                    Toast.makeText(home_login.this, "Login Sucessful"+ "\nWelcome, " + username + "!", Toast.LENGTH_LONG).show();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Client");
                    query.whereEqualTo("Email_Address", username);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject player, ParseException e) {
                            if (e == null) {
                                Toast.makeText(home_login.this, player.getString("Access_Level"), Toast.LENGTH_LONG).show();
                                if(player.getString("Access_Level").equals("User"))
                                {
                                    Intent intent1 = new Intent(home_login.this, userActivity.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent1);
                                }
                                else if(player.getString("Access_Level").equals("Consultant"))
                                {
                                    Intent intent3 = new Intent(home_login.this, consultantHome.class);
                                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent3);
                                }

                            }
                        }
                    });

                }
                else if(ID_user!=parseUser.getInt("Access_Level"))
                    alertDisplayer("Login Fail", "It seems like you the role you have chosen from the dropdown menu in the toolbar doesn't match with your access level. Choose again.", true);

                else
                {
                    ParseUser.logOut();
                    alertDisplayer("Login Fail", "It seems like our admin hasn't confirmed your application. We will reach you shortly.", true);
                }
                Toast.makeText(home_login.this, "Sucessful Login"+"Welcome back" + username + "!",Toast.LENGTH_LONG).show();
            } else {
                ParseUser.logOut();
                Toast.makeText(home_login.this, "Wrong username or password", Toast.LENGTH_LONG).show();
            }
        }
    });
    }



}

