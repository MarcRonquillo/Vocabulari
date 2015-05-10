package com.idi.marc.vocabulari;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.app.AlertDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import android.content.Context;


public class MainActivity extends ActionBarActivity {

    private Traduccio trad;
    private String pathBD;
    static Context myContext;
    private static final String TAG = "MyActivity";
    private String tradString;
    //L'extra message hauria de ser un objecte de tipus Traduccio que fos new la primera execució
    //i que les següents vingués d'un fitxer per conservar la persistència

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myContext=this;

        inicialitzar();

    }

   /* @Override
    protected void onDestroy(){
        super.onDestroy();

        SharedPreferences settings2 = getSharedPreferences("traduccioPref2",0);
        SharedPreferences.Editor editor = settings2.edit();
        //editor.clear();
        //editor.commit();
        //String hola="hola";
        Traduccio tradu=new Traduccio();
        try {
            tradu.nouIdioma("suajili");
        }
        catch(Exception e){
            finestraAvis(e.getMessage());
        }

        editor.clear();
        String hhh=objectToString(tradu);
        editor.putString("traduccio",hhh);

        // Commit the edits!
        editor.commit();

        Log.i(TAG,"ondestroy         "+tradu.getLlistaIdiomes().get(0));

    }

    @Override
    protected void onPause() {

        super.onPause();
        //exportar(pathBD);

        /*tradString=objectToString(trad);
        Log.i(TAG, tradString);

        SharedPreferences settings = getSharedPreferences("traduccioPref",0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();/*
        editor.putString("traduccio",tradString);

        // Commit the edits!
        editor.commit();




    }*/

    public void inicialitzar(){

       trad=new Traduccio();
        try {
            trad.inicialitzar();
        }
        catch(Exception e){
            finestraAvis(getStackTrace(e));
        }
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public void activityEditar(View view) {
        Intent intent = new Intent(this, EditarActivity.class);

        intent.putExtra("trad",trad);
        startActivity(intent);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public  void onClickJoc(View view){

        Intent intent = new Intent(this, JocActivity.class);
        intent.putExtra("trad",trad);
        startActivity(intent);

    }

    public void onClickHelp(View view){
        String help="Aplicació per jugar amb idiomes, paraules i les seves traduccions.\n\n" +
                "A la primera pantalla l'usuari ha de triar entre editar els idiomes i les paraules o començar a jugar. Per defecte vénen dos idiomes i unes quantes paraules.\n\n" +
                "Si l'usuari tria editar, l'aplicació el portarà a una nova pantalla. En aquesta, hi ha uns desplegables per triar idiomes, paraules i traduccions. Uns botons ajuden a gestionar tota aquesta informació." +
                "Es poden afegir i esborrar idiomes, paraules i traduccions entre paraules. A mésm es poden visualitzar totes les traduccions assignades a una paraula.\n\n" +
                "Tornant a la pantalla principal, si l'usuari tria Jugar! l'aplicació el portarà a una nova pantalla per seleccionar el tipus de joc. Hi ha dos desplegables, l'idioma de partida és aquell del qual s'hauran de traduïr les paraules a l'altre idioma triat a l'altre desplegable. No es pot jugar amb idiomes que no tenen paraules o traduccions associades." +
                "A mode es poden triar 3 modes: 10 paraules, 20 paraules i 30 segons. Els dos primers són un mode de joc que farà acabar aquest quan s'hagi arribat a la xifra de paraules traduïdes. El mode 30 segons deixa jugar a l'usuari durant 30 segons." +
                "A la part inferior esquerra es troba el botó estadístiques, que permet visualitzar ordenades de millor a pitjor les puntuacions obtingudes als jocs. El botó Start permet començar el joc.\n\n" +
                "Al joc, l'usuari rep paraules aleatòries de l'idioma de partida triat i ha d'escriure la seva traducció a l'espai 'traducció'. Apretant el botó OK es comprova la resposta donada i es rep la següent paraula. Una resposta correcte és un punt. Les puntuacions i errors de la partida es visualitzen a la part superior de la pantalla. Si el mode triat és de 30 segons," +
                "es veu el temps que queda perquè la partida acabi. Quan la partida acaba, l'usuari rep un missatge que l'informa de la puntuació obtinguda i el retorna a la pantalla anterior.";
        finestraAvis(help);


    }

    public void onClickAbout(View view){

        String about="ABOUT\nAplicació Aprenent vocabulari v1.0"+"\n"+"Autor: Marc Ronquillo González. 2015"+"\n"+"Android 4.3.1 API 18"+"\n"+"Interacció i disseny d'interfícies";
        finestraAvis(about);
    }


}
