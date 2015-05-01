package com.idi.marc.vocabulari;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.app.AlertDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import android.content.Context;


public class MainActivity extends ActionBarActivity {

    private Traduccio trad;
    private String pathBD;
    static Context myContext;
    //L'extra message hauria de ser un objecte de tipus Traduccio que fos new la primera execució
    //i que les següents vingués d'un fitxer per conservar la persistència

    public void inicialitzar(){

       // String test=this.getFilesDir().getAbsolutePath();
        File f = new File(pathBD);
        if(f.exists() && !f.isDirectory()) {
            //Llegir l'arxiu i assignar-lo a trad

        importar(pathBD);

        }
        else{
            trad = new Traduccio();
            try {
                trad.inicialitzar();
            }
            catch (Exception e){
                finestraAvis(e.getMessage());
            }

        }
    }


    public void importar(String fitxer) {

        try {
            /*ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer));
            Traduccio aux = (Traduccio) ois.readObject();
            trad=aux;
           Log.d("Vocabulari",trad.getParaula("casa", "catala").getId());
            Log.d("Vocabulari","Hola");*/

            FileInputStream fis = myContext.openFileInput(fitxer);
            ObjectInputStream is = new ObjectInputStream(fis);
            Traduccio aux = (Traduccio) is.readObject();
            trad=aux;
            is.close();
            fis.close();

        }
        catch (Exception e){
            String avis=getStackTrace(e);
            finestraAvis(avis);
        }

    }

   public void exportar(String fitxer)  {

        try {
            /*ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fitxer));
            oos.writeObject(trad);
            oos.close();*/

            FileOutputStream fos = myContext.openFileOutput(fitxer, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(trad);
            os.close();
            fos.close();
        }
        catch (Exception e){
            String avis=getStackTrace(e);
            finestraAvis(avis);
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
        //El que s'ha de passar amb putExtra és l'objecte Traduccio. És un key-value, com un hashMap
        /*EditText editText = (EditText) findViewById(R.id.nomApp);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/
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
    protected void onPause(){

        super.onPause();
        exportar(pathBD);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myContext=this;
        //pathBD=myContext.getFilesDir().getAbsolutePath()+"/bd.dat";
        pathBD="bd.dat";
        inicialitzar();

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


}
