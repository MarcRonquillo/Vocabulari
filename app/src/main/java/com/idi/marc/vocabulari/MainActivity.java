package com.idi.marc.vocabulari;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    public Traduccio trad = new Traduccio();
    //L'extra message hauria de ser un objecte de tipus Traduccio que fos new la primera execució
    //i que les següents vingués d'un fitxer per conservar la persistència
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void activityEditar(View view) {
        Intent intent = new Intent(this, EditarActivity.class);
        //El que s'ha de passar amb putExtra és l'objecte Traduccio. És un key-value, com un hashMap
        /*EditText editText = (EditText) findViewById(R.id.nomApp);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/
        intent.putExtra("trad",trad);
        startActivity(intent);
    }
}
