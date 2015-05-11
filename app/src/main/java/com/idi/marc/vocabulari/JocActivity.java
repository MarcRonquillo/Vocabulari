package com.idi.marc.vocabulari;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;


public class JocActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Intent intentPrevi;
    private Traduccio trad;
    static Context myContext;
    private Spinner spinnerIdioma1;
    private Spinner spinnerIdioma2;
    private Spinner spinnerMode;
    private Idioma idioma1;
    private Idioma idioma2;
    private String mode;
    private ArrayList<String> modes;
    private boolean llistaIdiomesBuida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joc);

        intentPrevi = getIntent();
        trad = (Traduccio) intentPrevi.getSerializableExtra("trad");
        myContext = this;

        modes=new ArrayList<String>();
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
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getIdiomesAmbParaules());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdioma1.setAdapter(adaptador);

    }

    public void addListenerOnSpinnerItemSelectionIdioma1() {

        spinnerIdioma1 = (Spinner) findViewById(R.id.spinnerIdioma1);
        spinnerIdioma1.setOnItemSelectedListener(this);

    }

    public void setSpinnerIdioma2(){

        spinnerIdioma2 = (Spinner) findViewById(R.id.spinnerIdioma2);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaIdiomesAmbTraduccio(idioma1.getId()));
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdioma2.setAdapter(adaptador);

        if(trad.getLlistaIdiomesAmbTraduccio(idioma1.getId()).isEmpty())
            llistaIdiomesBuida=true;
        else
            llistaIdiomesBuida=false;
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
                Traduccio.mode=mode;
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClickStart(View view){

        if((idioma1!=null)&&(idioma2!=null)&&(!llistaIdiomesBuida)) {
            Traduccio.idioma1Joc=idioma1.getId();
            Traduccio.idioma2Joc=idioma2.getId();
            Traduccio.mode=mode;
            Intent intent = new Intent(this, JocActualActivity.class);
            intent.putExtra("trad",trad);
            startActivity(intent);
        }
    }

    public void onClickEstadistiques(View view){

        int i=0;
        String showable=new String();

        TreeMap<Integer,String> mapa=new TreeMap<Integer,String>();
        String aux;

        for(i=0;i<Traduccio.modesStatic.size();i++){
            aux=" Errors: "+Traduccio.erradesModeStatic.get(i).toString()+" Mode: "+Traduccio.modesStatic.get(i)+"\n";
            mapa.put(Traduccio.puntuacionsModeStatic.get(i),aux);
        }

        NavigableMap nav=mapa.descendingMap();
        Iterator<Map.Entry<Integer,String>> it = nav.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer,String> pair = it.next();
            showable+="Punts: "+pair.getKey().toString()+pair.getValue();
        }

                finestraAvis(showable);
    }

    public void finestraAvis(String input){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(myContext);
        builder1.setMessage(input);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


}