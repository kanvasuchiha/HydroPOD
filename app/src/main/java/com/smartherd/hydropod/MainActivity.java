package com.smartherd.hydropod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private String texts;
    private TextView txtDate, txtEmailS;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        txtEmailS = findViewById(R.id.txtEmailS);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        txtDate=findViewById(R.id.txtDate);
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

                String monthString = new DateFormatSymbols().getMonths()[month-1];

                String dat = date+" "+monthString+" "+year;
                txtDate.setText(dat);
            }
        };

    }


    private Boolean validateEmail() {
        String emailInput = txtEmailS.getText().toString().trim();

        if(emailInput.isEmpty())
        {
            Toast.makeText(MainActivity.this, "This field cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

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
