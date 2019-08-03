package com.smartherd.hydropod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener  {



    private Spinner spinner;
    private String texts;
    private TextView txtDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

//    private DrawerLayout drawer;


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    private TextInputLayout textEmail, textPassword, txtConfirmPassword;
    private EditText txtName, txtPhone, txtAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        setContentView(R.layout.activity_sign_up);



        /*


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer=findViewById(R.id.user_drawer);

        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        */






        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtConfirmPassword = findViewById(R.id.txt_confirm_password);
        textEmail = findViewById(R.id.txt_email);
        textPassword = findViewById(R.id.txt_password);



        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this); /////////////

        txtDate = findViewById(R.id.txtDate);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(SignUp.this,
                        android.R.style.Theme_Black_NoTitleBar, onDateSetListener, year, month, day);

                //TO MAKE BACKGROUND TRANSPARENT
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                String monthString = new DateFormatSymbols().getMonths()[month];

                String dat = date + " " + monthString + " " + year;
                txtDate.setText(dat);
            }
        };


    }


/*
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

*/



    private Boolean validateEmail() {
        String emailInput = textEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textEmail.setError("Please enter a valid email address");
            return false;

        } else {
            textEmail.setError(null);
            textEmail.setErrorEnabled(false);
        }
        return true;
    }


    private Boolean validatePassword() {
        String emailInput = textPassword.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textPassword.setError("Field can't be empty");
            return false;
        } else if (emailInput.length() <8) {
            textPassword.setError("Atleast have length 8");
            return false;
        } else if (emailInput.length() > 20) {
            textPassword.setError("Password too longgggggggggg........");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(emailInput).matches()) {
            textPassword.setError("Weak Password. Atleast have a small letter, a capital letter, a digit and a special character.");
            return false;
        } else {
            textPassword.setError(null);
            textPassword.setErrorEnabled(false);
        }
        return true;
    }

    private Boolean matchPasswords() {
        String passwordInput = textPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = txtConfirmPassword.getEditText().getText().toString().trim();
        if (!passwordInput.equals(confirmPasswordInput)) {
            txtConfirmPassword.setError("Passwords do not match.");
            return false;
        } else {
            txtConfirmPassword.setError(null);
            txtConfirmPassword.setErrorEnabled(false);
        }
        return true;
    }




    public void confirmInput(View v) throws java.text.ParseException {

        if (!validateEmail() | !validatePassword() | !matchPasswords() && txtName.getText().toString().isEmpty() && txtPhone.getText().toString().isEmpty() && txtAddress.getText().toString().isEmpty()) {
            Toast.makeText(SignUp.this, "Some fields are empty or wrong. Check above", Toast.LENGTH_SHORT).show();
            return;
        }



        Date date1 = new SimpleDateFormat("dd MMMMM yyyy").parse(txtDate.getText().toString());

        ParseObject client = new ParseObject("Client");
        ParseUser user = new ParseUser();
        client.put("Full_Name", txtName.getText().toString());
        client.put("Sex", spinner.getSelectedItem().toString());
        client.put("DOB", date1);
        client.put("Mobile_Number", txtPhone.getText().toString());
        client.put("Email_address", textEmail.getEditText().getText().toString().trim());
        client.put("Current_address", txtAddress.getText().toString());



        client.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //Toast.makeText(MainActivity.this, "Congratulation "+txtName.getText().toString()+"!\nYou have successfully signed up", Toast.LENGTH_LONG).show();
                } else {
                    ParseUser.logOut();
                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });


        user.setPassword(textPassword.getEditText().getText().toString().trim());
        user.setUsername(textEmail.getEditText().getText().toString().trim());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //alertDisplayer("Sucessful Sign Up!","Welcome" + <Insert Username Here> + "!");
                    Toast.makeText(SignUp.this, "Congratulation "+txtName.getText().toString()+"!\nYou have successfully signed up as user", Toast.LENGTH_LONG).show();

                } else {
                    ParseUser.logOut();
                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        texts = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /*
    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(MainActivity.this, LogoutActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
    */





}
