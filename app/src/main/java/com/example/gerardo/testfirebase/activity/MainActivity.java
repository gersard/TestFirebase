package com.example.gerardo.testfirebase.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.testfirebase.FKeys;
import com.example.gerardo.testfirebase.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editUsuario,editClave;
    Button btnEnviar;
    Firebase firebase;
    String usuario;
    TextView registrarse;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_bienvenida);
        setSupportActionBar(toolbar);

        progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("Autentificando...");
        progress.setIndeterminate(true);
        progress.setCancelable(false);

        //Indicamos el contexto
        Firebase.setAndroidContext(this);
        //Manejo de los datos Offline
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        firebase = new Firebase(FKeys.FIREBASE_URL).child(FKeys.FIREBASE_CHILD);

        registrarse = (TextView) findViewById(R.id.txt_registrar);
        editUsuario = (EditText) findViewById(R.id.edit_usuario);
        editClave = (EditText) findViewById(R.id.edit_clave);
        editClave.setTypeface(Typeface.DEFAULT);
        btnEnviar = (Button) findViewById(R.id.btn_enviar);
        btnEnviar.setOnClickListener(this);
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onClick(View v) {
        progress.show();
        firebase.authWithPassword(editUsuario.getText().toString(), editClave.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Intent intent = new Intent(getApplicationContext(),InicioActivity.class);
                        usuario = editUsuario.getText().toString();
                        usuario.replace("@gmail.com","");
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), "Usuario/Contraseña incorrecto"
                                , Toast.LENGTH_SHORT)
                                .show();
                        progress.cancel();
                    }
                }
        );
    }


}
