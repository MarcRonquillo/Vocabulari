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

    public  void onClickJoc(View view){

        Intent intent = new Intent(this, JocActivity.class);
        intent.putExtra("trad",trad);
        startActivity(intent);

    }

    public void onClickHelp(View view){
        String help="Aplicació per jugar amb idiomes, paraules i les seves traduccions.\n" +
                "A la primera pantalla l'usuari he de triar entre editar els idiomes i les paraules o començar a jugar. Per defecte vénen dos idiomes i unes quantes paraules.\n" +
                "Si l'usuari tria editar, l'aplicació el portarà a una nova pantalla. En aquesta, hi ha uns desplegables per triar idiomes, paraules i traduccions. Uns botons ajuden a gestionar tota aquesta informació." +
                "Es poden afegir i esborrar idiomes, paraules i traduccions entre paraules. A mésm es poden visualitzar totes les traduccions assignades a una paraula.\n" +
                "Tornant a la pantalla principal, si l'usuari tria Jugar! l'aplicació el portarà a una nova pantalla per seleccionar el tipus de joc. Hi ha dos desplegables, l'idioma de partida és aquell del qual s'hauran de traduïr les paraules a l'altre idioma triat a l'altre desplegable. No es pot jugar amb idiomes que no tenen paraules o traduccions associades." +
                "A mode es poden triar 3 modes: 10 paraules, 20 paraules i 30 segons. Els dos primers són un mode de joc que farà acabar aquest quan s'hagi arribat a la xifra de paraules traduïdes. El mode 30 segons deixa jugar a l'usuari durant 30 segons." +
                "Es pot trobar el botó estadístiques, que permet visualitzar ordenades de millor a pitjor les puntuacions obtingudes als jocs. El botó Start permet començar el joc.\n" +
                "Al joc, l'usuari rep paraules aleatòries de l'idioma de partida triat i ha d'escriure la seva traducció a l'espai 'traducció'. Apretant el botó OK es comprova la resposta donada i es rep la següent paraula. Una resposta correcte és un punt. Les puntuacions i errors de la aprtida es visualitzen a la part superior de la pantalla. Si el mode triat es de 30 segons," +
                "es veu el temps que queda perquè la partida acabi. Quan la partida acaba, l'usuari rep un missatge que l'informa d'això, de la puntuació realitzada i el retorna a la pantalla anterior.";
        finestraAvis(help);


    }

    public void onClickAbout(View view){

        String about="ABOUT\nAplicació Aprenent vocabulari v1.0"+"\n"+"Autor: Marc Ronquillo González. 2015"+"\n"+"Android 4.3.1 API 18"+"\n"+"Interacció i disseny d'interfícies";
        finestraAvis(about);
    }

    /*
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
*/
}
