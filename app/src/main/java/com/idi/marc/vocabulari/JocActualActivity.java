package com.idi.marc.vocabulari;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class JocActualActivity extends Activity {

    private Intent intentPrevi;
    private Traduccio trad;
    static Context myContext;
    private TextView temps;
    private TextView parATrad;
    private final long startTime = 30000;
    private final long interval = 1000;
    private VocabulariCountDownTimer comptador;
    private String paraulaTraduida;
    private Integer numRespostes,errors,encerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joc_actual);

        intentPrevi = getIntent();
        trad = (Traduccio) intentPrevi.getSerializableExtra("trad");
        myContext = this;
        paraulaTraduida=new String();
        parATrad = (TextView) findViewById(R.id.paraulaATraduir);
        temps = (TextView) findViewById(R.id.temps);
        TextView idi1=(TextView) findViewById(R.id.idioma1);idi1.setText(trad.idioma1Joc);
        TextView idi2=(TextView) findViewById(R.id.idioma2);idi2.setText(trad.idioma2Joc);
        numRespostes=0;
        errors=0;
        encerts=0;
        setTitol();
        trad.crearJoc(Traduccio.idioma1Joc,Traduccio.idioma2Joc);
        setErrorsPunts(0, 0);
        setTimerONo();
        enviarParaula();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_joc_actual, menu);
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

    public void setTitol(){
        TextView myTextView = (TextView) findViewById(R.id.titolModeJoc);
                myTextView.setText(trad.mode);

    }

    public void setErrorsPunts(Integer punts, Integer errors){

        TextView puntuacio = (TextView) findViewById(R.id.Punts);
        puntuacio.setText(punts.toString());

        TextView errades = (TextView) findViewById(R.id.Errades);
        errades.setText(errors.toString());
    }

    public void setTimerONo(){
        if(trad.mode.equals("30 segons")){
            comptador=new VocabulariCountDownTimer(startTime, interval);
            comptador.start();
        }
    }

    public void enviarParaula(){
        parATrad.setText(trad.enviarParaula());
    }

    public void onClickOk(View view){

        EditText par = (EditText) findViewById(R.id.traduccio);
        paraulaTraduida=par.getText().toString();
        par.setText("", TextView.BufferType.EDITABLE);

        if(trad.rebreParaula(paraulaTraduida)){
           encerts++;
        }
        else{
            errors++;
        }
        setErrorsPunts(encerts,errors);
        numRespostes++;
        comprovarSiFinish();
        enviarParaula();
    }

    public void comprovarSiFinish(){

        switch(Traduccio.mode){
            case "10 paraules":
                if(numRespostes>=10)
                finestraAvis("Joc acabat. Encerts: "+encerts.toString()+" Errors: "+errors.toString());
                break;
            case "20 paraules":
                if(numRespostes>=20)
                    finestraAvis("Joc acabat. Encerts: " + encerts.toString() + " Errors: " + errors.toString());
                break;
        }

    }

    public void finestraAvis(String input){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(myContext);
        builder1.setMessage(input);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Traduccio.modesStatic.add(Traduccio.mode);
                        Traduccio.erradesModeStatic.add(errors);
                        Traduccio.puntuacionsModeStatic.add(encerts);
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    public class VocabulariCountDownTimer extends CountDownTimer
    {

        public VocabulariCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            temps.setText("0");
            finestraAvis("Joc acabat. Encerts: "+encerts.toString()+" Errors: "+errors.toString());

        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            millisUntilFinished=millisUntilFinished/1000;
            temps.setText(String.valueOf(millisUntilFinished));
        }
    }
}
