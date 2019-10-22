package com.smartherd.hydropod;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class AdminLogin extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private Button button;
    private int ID_user;
    private String username, password;
    private TextInputLayout txt1, txt2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);




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
        if (id == R.id.login_user) {
            Intent intent1 = new Intent(AdminLogin.this, home_login.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
            //openDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void alertDisplayer(String title,String message, final boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminLogin.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent = new Intent(AdminLogin.this, SignUp.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

/*   public void loginSubmitAdmin(View view)
    {
        Intent intent2 = new Intent(AdminLogin.this, adminHome.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent2);


        username= txt1.getEditText().getText().toString().trim();
        password= txt2.getEditText().getText().toString().trim();
        Toast.makeText(AdminLogin.this, username+password, Toast.LENGTH_LONG).show();
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    if(parseUser.getBoolean("emailVerified") && ID_user==parseUser.getInt("Access_Level")) {
                        Toast.makeText(AdminLogin.this, "Login Sucessful"+ "\nWelcome, " + username + "!", Toast.LENGTH_LONG).show();

                        switch(ID_user){
                            case R.id.login_user:
                                Toast.makeText(AdminLogin.this, "Please select your role from the dropdown menu in the toolbar above", Toast.LENGTH_LONG).show();
                                break;



                        }

                    }
                    else
                    {
                        ParseUser.logOut();
                        alertDisplayer("Login Fail", "It seems like our admin hasn't confirmed your application. We will reach you shortly.", true);
                    }
                    Toast.makeText(AdminLogin.this, "Sucessful Login"+"Welcome back" + username + "!",Toast.LENGTH_LONG).show();
                } else {
                    ParseUser.logOut();
                    Toast.makeText(AdminLogin.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    */



    public void loginSubmitAdmin(View view)
    {
        username= txt1.getEditText().getText().toString().trim();
        password= txt2.getEditText().getText().toString().trim();
        Toast.makeText(AdminLogin.this, username+password, Toast.LENGTH_LONG).show();
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    if(parseUser.getBoolean("emailVerified")) {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Client");
                        query.whereEqualTo("Email_Address", username);
                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                            public void done(ParseObject player, ParseException e) {
                                if (e == null) {
                                    Toast.makeText(AdminLogin.this, player.getString("Access_Level"), Toast.LENGTH_LONG).show();
                                    if(player.getString("Access_Level").equals("Admin"))
                                    {
                                        Toast.makeText(AdminLogin.this, "Login Sucessful"+ "\nWelcome, " + username + "!", Toast.LENGTH_LONG).show();
                                        Intent intent1 = new Intent(AdminLogin.this, adminHome.class);
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent1);
                                    }
                                    else
                                    {
                                        alertDisplayer("Login Fail", "It seems like you did not signup for the admin privileges.", true);

                                    }

                                }
                            }
                        });

                    }

                    else
                    {
                        ParseUser.logOut();
                        alertDisplayer("Login Fail", "It seems like our admin hasn't confirmed your application. We will reach you shortly.", true);
                    }
                    Toast.makeText(AdminLogin.this, "Sucessful Login"+"Welcome back" + username + "!",Toast.LENGTH_LONG).show();
                } else {
                    ParseUser.logOut();
                    Toast.makeText(AdminLogin.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
