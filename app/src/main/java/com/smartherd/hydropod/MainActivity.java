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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private String texts;
    private TextView txtDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    //".{4,}" +               //at least 4 characters
                    "$");


    private TextInputLayout textEmail, textPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        textEmail = findViewById(R.id.txt_email);
        textPassword = findViewById(R.id.txt_password);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        txtDate = findViewById(R.id.txtDate);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                        android.R.style.Theme_DeviceDefault_Light_DarkActionBar, onDateSetListener, year, month, day);

                //TO MAKE BACKGROUND TRANSPARENT
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                String monthString = new DateFormatSymbols().getMonths()[month - 1];

                String dat = date + " " + monthString + " " + year;
                txtDate.setText(dat);
            }
        };

    }


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
        } else if (emailInput.length() < 9) {
            textPassword.setError("Atleast have length 8");
            return false;
        } else if (emailInput.length() > 20) {
            textPassword.setError("Password too longgggggggggg........");
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(emailInput).matches())
        {
            textPassword.setError("Weak Password. Atleast have a small letter, a capital letter, a digit and a special character.");
            return false;
        }
        else {
            textPassword.setError(null);
            textPassword.setErrorEnabled(false);
        }
        return true;
    }


    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword()) {
            return;
        }

        Toast.makeText(MainActivity.this, "Successfully signed up", Toast.LENGTH_LONG).show();
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
}
