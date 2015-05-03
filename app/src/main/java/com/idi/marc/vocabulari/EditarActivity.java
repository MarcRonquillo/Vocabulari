package com.idi.marc.vocabulari;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;


public class EditarActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    Intent intentPrevi;
    Traduccio trad;
    private Spinner spinnerIdioma;
    private Spinner spinnerParaula;
    private Spinner spinnerTraduccio;
    private Spinner spinnerBorrar;
    private Spinner spinnerAfegir;
    private Idioma idiomaActual;
    private Idioma idiomaOut;
    private Paraula paraulaActual;
    private Paraula paraulaAAfegir;
    private Paraula paraulaAEsborrar;
    static Context myContext;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        intentPrevi = getIntent();
        trad = (Traduccio) intentPrevi.getSerializableExtra("trad");
        myContext = this;
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

    public void setSpinnerIdioma() {

        spinnerIdioma = (Spinner) findViewById(R.id.spinnerIdiomes);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaIdiomes());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdioma.setAdapter(adaptador);


    }

    public void setSpinnerParaula() {

        spinnerParaula = (Spinner) findViewById(R.id.spinnerParaules);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaParaules(idiomaActual.getId()));
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParaula.setAdapter(adaptador);

    }

    public void setSpinnerTraduccions() {
        spinnerTraduccio = (Spinner) findViewById(R.id.spinnerTraduccions);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaIdiomesMenysPropi(idiomaActual.getId()));
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTraduccio.setAdapter(adaptador);

    }

    public void setSpinnerEsborrarTraduccions(){
        spinnerBorrar = (Spinner) findViewById(R.id.spinnerEsborrarTrad);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getTraduccio(paraulaActual.getId(), idiomaActual.getId(),idiomaOut.getId()));
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBorrar.setAdapter(adaptador);
    }

    public void setSpinnerAfegirTraduccions(){
        spinnerAfegir = (Spinner) findViewById(R.id.spinnerAfegirTrad);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trad.getLlistaParaules(idiomaOut.getId()));
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAfegir.setAdapter(adaptador);
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

    public void addListenerOnSpinnerSelectionBorrar() {
        spinnerBorrar = (Spinner) findViewById(R.id.spinnerEsborrarTrad);
        spinnerBorrar.setOnItemSelectedListener(this);
    }

    public void addListenerOnSpinnerSelectionTrad() {
        spinnerAfegir = (Spinner) findViewById(R.id.spinnerAfegirTrad);
        spinnerAfegir.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

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
                for (String details : trad.getTraduccio(paraulaActual.getId(), idiomaActual.getId(), (String) parent.getItemAtPosition(pos))) {
                    builder.append(details + ", ");
                }
                myTextView.setText(builder.toString());
                idiomaOut=trad.getIdioma((String) parent.getItemAtPosition(pos));
                setSpinnerAfegirTraduccions();
                addListenerOnSpinnerSelectionTrad();
                setSpinnerEsborrarTraduccions();
                addListenerOnSpinnerSelectionBorrar();
                break;
            case R.id.spinnerAfegirTrad:

                paraulaAAfegir=trad.getParaula((String) parent.getItemAtPosition(pos),idiomaOut.getId());
                   /* trad.connectarParaules(paraulaActual.getId(),idiomaActual.getId(),(String) parent.getItemAtPosition(pos),idiomaOut.getId());
                    setSpinnerIdioma();*/

                break;
            case R.id.spinnerEsborrarTrad:

                paraulaAEsborrar=trad.getParaula((String) parent.getItemAtPosition(pos),idiomaOut.getId());

                    /*trad.removeTraduccio(paraulaActual.getId(),idiomaActual.getId(),(String) parent.getItemAtPosition(pos),idiomaOut.getId());
                    setSpinnerIdioma();*/

                break;
        }

    }

public void actualitzarSpinners(){
    setSpinnerIdioma();
    setSpinnerParaula();
    setSpinnerTraduccions();
    setSpinnerEsborrarTraduccions();
    setSpinnerAfegirTraduccions();
}
public void finestraOkCancel(int i){

    AlertDialog.Builder adb = new AlertDialog.Builder(myContext);

        switch (i) {
            //finestra esborrar idioma
            case 1:
                adb.setMessage("Segur?");
                adb.setCancelable(true);
                adb.setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    trad.eliminarIdioma(idiomaActual.getId());
                                    actualitzarSpinners();
                                }
                                catch(Exception e){
                                    String avis = getStackTrace(e);
                                    finestraAvis(avis);
                                }
                            }
                        });
                break;
            case 2:
                adb.setMessage("Segur?");
                adb.setCancelable(true);
                adb.setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                    trad.eliminarParaula(paraulaActual.getId(),idiomaActual.getId());
                                    actualitzarSpinners();
                            }
                        });
                break;
        }
    adb.setNegativeButton("No",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

    AlertDialog alert11 = adb.create();
    alert11.show();
}

    public void onClickEliminarIdioma(View view){
        finestraOkCancel(1);
    }

    public void onClickEliminarParaula(View view){
        finestraOkCancel(2);
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
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

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
