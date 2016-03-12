package com.example.gerardo.testfirebase.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerardo.testfirebase.FKeys;
import com.example.gerardo.testfirebase.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editUsuario,editClave;
    Button registrar;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_registrar);
        setSupportActionBar(toolbar);

        editUsuario = (EditText) findViewById(R.id.edit_usuario_reg);
        editClave = (EditText) findViewById(R.id.edit_clave_reg);
        registrar = (Button) findViewById(R.id.btn_enviar_reg);

        Firebase.setAndroidContext(this);
        firebase = new Firebase(FKeys.FIREBASE_URL);

        registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String correo = editUsuario.getText().toString();
        String clave = editClave.getText().toString();
        if (correo == null){
            Toast.makeText(getApplicationContext(),"Ingrese un correo valido",Toast.LENGTH_SHORT).show();
        }else{
            if (clave == null){
                Toast.makeText(getApplicationContext(),"Ingrese una clave",Toast.LENGTH_SHORT).show();
            }else{
                firebase.createUser(correo, clave, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        Toast.makeText(getApplicationContext(),"Usuario creado exitosamente",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(),firebaseError.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


    }
}
