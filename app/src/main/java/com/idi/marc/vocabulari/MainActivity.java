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
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import android.content.Context;


public class MainActivity extends ActionBarActivity {

    private Traduccio trad;
    private String pathBD;
    static Context myContext;
    private static final String TAG = "MyActivity";
    //L'extra message hauria de ser un objecte de tipus Traduccio que fos new la primera execució
    //i que les següents vingués d'un fitxer per conservar la persistència

    public void inicialitzar(){
/*
       // String test=this.getFilesDir().getAbsolutePath();
        File f = new File(myContext.getFilesDir().getPath()+"/"+"bd.dat");
        if(f.exists() && !f.isDirectory()) {
            //Llegir l'arxiu i assignar-lo a trad
            Log.i(TAG, "fitxer trobat, important");
        importar(pathBD);


        }
        else{
            Log.i(TAG, "fitxer no trobat, generant nou bd.dat");
            Log.i(TAG, this.getFilesDir().getAbsolutePath()+"/"+"bd.dat");
            Log.i(TAG, this.getFilesDir().getPath()+"/"+"bd.dat");

            trad = new Traduccio();
            try {
                trad.inicialitzar();
            }
            catch (Exception e){
                finestraAvis(e.getMessage());
            }

        }*/
        trad = new Traduccio();
        try {
            trad.inicialitzar();
        }
        catch (Exception e){
            finestraAvis(e.getMessage());
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
            trad = (Traduccio) is.readObject();
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
            eliminarFitxer();
            FileOutputStream fos = myContext.openFileOutput(fitxer, myContext.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(trad);
            os.flush();
            os.close();
            fos.close();
        }
        catch (Exception e){
            String avis=getStackTrace(e);
            finestraAvis(avis);
        }
    }

    public void eliminarFitxer(){
        File file=new File(myContext.getFilesDir().getPath()+"/"+"bd.dat");
        if(file.exists()) {
            Log.i(TAG, "esborrant arxiu");
            file.delete();
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
        //exportar(pathBD);
        Log.i(TAG, "guardant");

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

    public  void guardar(View view){

        //Log.i(TAG, trad.getIdioma("català").getParaula("ordinador").getId());
        //exportar(pathBD);
        /*String test1=new String("Si");
        String test2=new String("No");*/

       /* Traduccio tradu=new Traduccio();

        try {
            tradu.nouIdioma("suajili");
        }
        catch (Exception e){
            String avis=getStackTrace(e);
            finestraAvis(avis);
        }

        try {
            File file=new File(getFilesDir().getPath()+"/test1.dat");
            if(file.exists()) {
                Log.i(TAG, "esborrant arxiu");
                file.delete();
            }
            FileOutputStream fos = openFileOutput("test1.dat", myContext.MODE_WORLD_READABLE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(tradu);
            os.flush();
            os.close();
            fos.close();
        }
        catch (Exception e){
            String avis=getStackTrace(e);
            finestraAvis(avis);
        }*/
        // add-write text into file
        try {
            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("prova");
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

   /* public static void save(){
        exportar("bd.dat");
        //Log.i(TAG, "idioma=" + idiomaActual.getId());
    }*/

    public void onClickObrir(View view){
        /*Traduccio tradu2;

        try {
            FileInputStream fis = openFileInput("test1.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            tradu2 = (Traduccio) is.readObject();
            is.close();
            fis.close();
            Log.i(TAG, tradu2.getIdioma("suajili").getId());
        }
        catch (Exception e){
            String avis=getStackTrace(e);
            finestraAvis(avis);
        }*/
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[256];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Log.i(TAG, s);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void WriteBtn() {
        // add-write text into file
        try {
            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write("prova");
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public void ReadBtn() {
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[256];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Log.i(TAG, s);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
