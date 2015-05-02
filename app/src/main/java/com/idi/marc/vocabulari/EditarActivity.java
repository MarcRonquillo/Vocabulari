package com.idi.marc.vocabulari;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class EditarActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    Intent intentPrevi;
    Traduccio trad;
    private Spinner spinnerIdioma;
    private Spinner spinnerParaula;
    private Spinner spinnerTraduccio;
    private Idioma idiomaActual;
    private Paraula paraulaActual;
    static Context myContext;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        intentPrevi=getIntent();
        trad=(Traduccio)intentPrevi.getSerializableExtra("trad");

        myContext=this;
        setSpinnerIdioma();
        addListenerOnSpinnerItemSelectionIdioma();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar, menu);
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

    public void setSpinnerIdioma(){

        spinnerIdioma = (Spinner) findViewById(R.id.spinnerIdiomes);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaIdiomes());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdioma.setAdapter(adaptador);


    }

    public void setSpinnerParaula(){

        spinnerParaula = (Spinner) findViewById(R.id.spinnerParaules);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaParaules(idiomaActual.getId()));
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParaula.setAdapter(adaptador);

    }

    public void setSpinnerTraduccions(){
        spinnerTraduccio = (Spinner) findViewById(R.id.spinnerTraduccions);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaIdiomesMenysPropi(idiomaActual.getId()));
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTraduccio.setAdapter(adaptador);

    }

    public void addListenerOnSpinnerItemSelectionIdioma() {
        spinnerIdioma = (Spinner) findViewById(R.id.spinnerIdiomes);
        spinnerIdioma.setOnItemSelectedListener(this);

    }

    public void addListenerOnSpinnerSelectionParaula() {
        spinnerParaula = (Spinner) findViewById(R.id.spinnerParaules);
        spinnerParaula.setOnItemSelectedListener(this);
    }

    public void addListenerOnSpinnerSelectionTraduccio() {
        spinnerTraduccio = (Spinner) findViewById(R.id.spinnerTraduccions);
        spinnerTraduccio.setOnItemSelectedListener(this);

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        switch (parent.getId()) {
            case R.id.spinnerIdiomes:
                idiomaActual = trad.getIdioma((String) parent.getItemAtPosition(pos));
                setSpinnerParaula();
                addListenerOnSpinnerSelectionParaula();
                //Log.i(TAG, "idioma=" + idiomaActual.getId());
                break;

            case R.id.spinnerParaules:
                paraulaActual = trad.getParaula((String) parent.getItemAtPosition(pos), idiomaActual.getId());
                setSpinnerTraduccions();
                addListenerOnSpinnerSelectionTraduccio();
                break;

            case R.id.spinnerTraduccions:
                TextView myTextView = (TextView) findViewById(R.id.traduccionsText);
                StringBuilder builder = new StringBuilder();
                for (String details : trad.getTraduccio(paraulaActual.getId(),idiomaActual.getId(),(String) parent.getItemAtPosition(pos))) {
                    builder.append(details + ", ");
                }
                myTextView.setText(builder.toString());
                break;
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
