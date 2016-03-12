package com.example.gerardo.testfirebase;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Gerardo on 16-02-2016.
 */
public class LoginApplication extends Application {

    Firebase firebase;

    @Override
    public void onCreate() {
        super.onCreate();
        //Indicamos el contexto
        Firebase.setAndroidContext(this);
        //Manejo de los datos Offline
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        firebase = new Firebase(FKeys.FIREBASE_URL).child(FKeys.FIREBASE_CHILD);
    }

    public Firebase getFirebase() {
        return firebase;
    }
}
