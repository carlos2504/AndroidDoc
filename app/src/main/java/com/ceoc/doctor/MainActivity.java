package com.ceoc.doctor;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePicker.OnTimeChangedListener, DatePicker.OnDateChangedListener, View.OnClickListener {
private String selected_especialidad="";
private Calendar calendar= Calendar.getInstance();
private Scene scene1, scene2;
private Transition transition;
private boolean start;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Spinner
        Spinner spinner= findViewById(R.id.txt_especialidad);
        //Adapter con el arreglo especialidades_array que es sonde se encuentran las especialidades médicas
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.especialidades_array, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //Time Picker
        TimePicker timePicker=findViewById(R.id.txt_hora);
        timePicker.setOnTimeChangedListener(this);
        //Date Picker
        DatePicker datePicker=findViewById(R.id.txt_fecha);
        datePicker.setOnDateChangedListener(this);
        //Image button
        ImageButton imageButton=findViewById(R.id.txt_cita);
        imageButton.setOnClickListener(this);
        //Transición
        //Escena actual
        ViewGroup mSceneRoot= (ViewGroup) findViewById(R.id.escena);
        //Escenas 1 y 2 que serán activity_main y confirmacion
        scene1= Scene.getSceneForLayout(mSceneRoot, R.layout.activity_main, this);
        scene2= Scene.getSceneForLayout(mSceneRoot, R.layout.confirmacion, this);
        //Se crea la transición
        transition=new AutoTransition();
        transition.setDuration(100);
        transition.setInterpolator(new AccelerateDecelerateInterpolator());
        start= true;
    }


    @Override
    public void onClick(View view) {
        DateFormat dateFormat= SimpleDateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        String date=dateFormat.format(calendar.getTime());
        Toast.makeText(this, getString(R.string.selected_data, selected_especialidad, date), Toast.LENGTH_LONG).show();
        //Transición
        if(start){
            TransitionManager.go(scene2, transition);
            start= false;
        }else{
            TransitionManager.go(scene1, transition);
            start= true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selected_especialidad=adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
       selected_especialidad="";
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);


    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
    }
}