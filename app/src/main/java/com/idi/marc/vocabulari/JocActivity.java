package com.idi.marc.vocabulari;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class JocActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    Intent intentPrevi;
    Traduccio trad;
    static Context myContext;
    Spinner spinnerIdioma1;
    Spinner spinnerIdioma2;
    Spinner spinnerMode;
    Idioma idioma1;
    Idioma idioma2;
    String mode;
    ArrayList<String> modes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joc);

        intentPrevi = getIntent();
        trad = (Traduccio) intentPrevi.getSerializableExtra("trad");
        myContext = this;

        modes.add("10 paraules");
        modes.add("20 paraules");
        modes.add("30 segons");
        setSpinnerIdioma1();
        addListenerOnSpinnerItemSelectionIdioma1();
        setSpinnerMode();
        addListenerOnSpinnerItemSelectionMode();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_joc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSpinnerIdioma1(){

        spinnerIdioma1 = (Spinner) findViewById(R.id.spinnerIdioma1);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaIdiomes());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdioma1.setAdapter(adaptador);

    }

    public void addListenerOnSpinnerItemSelectionIdioma1() {

        spinnerIdioma1 = (Spinner) findViewById(R.id.spinnerIdioma1);
        spinnerIdioma1.setOnItemSelectedListener(this);

    }

    public void setSpinnerIdioma2(){

        spinnerIdioma2 = (Spinner) findViewById(R.id.spinnerIdioma2);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaIdiomesMenysPropi(idioma1.getId()));
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdioma2.setAdapter(adaptador);
    }

    public void addListenerOnSpinnerItemSelectionIdioma2() {

        spinnerIdioma2 = (Spinner) findViewById(R.id.spinnerIdioma2);
        spinnerIdioma2.setOnItemSelectedListener(this);

    }

    public void setSpinnerMode(){
        spinnerMode = (Spinner) findViewById(R.id.spinnerMode);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, modes);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMode.setAdapter(adaptador);
    }

    public void addListenerOnSpinnerItemSelectionMode() {

        spinnerMode = (Spinner) findViewById(R.id.spinnerMode);
        spinnerMode.setOnItemSelectedListener(this);

    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        switch (parent.getId()) {
            case R.id.spinnerIdioma1:
                if(trad.getLlistaIdiomes().size()>=2) {
                    idioma1 = trad.getIdioma((String) parent.getItemAtPosition(pos));
                    setSpinnerIdioma2();
                    addListenerOnSpinnerItemSelectionIdioma2();
                }
                break;
            case R.id.spinnerIdioma2:
                idioma2=trad.getIdioma((String) parent.getItemAtPosition(pos));
                break;
            case R.id.spinnerMode:
                mode=(String)parent.getItemAtPosition(pos);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void onClickStart(View view){
        
    }
}
