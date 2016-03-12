package com.example.gerardo.testfirebase.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gerardo.testfirebase.FKeys;
import com.example.gerardo.testfirebase.R;
import com.example.gerardo.testfirebase.model.Pedido;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarFragment extends Fragment implements View.OnClickListener {

    Button verMenu,verAlmuerzo,verPostre,finalizar;
    Spinner spinner;
    Firebase firebase;
    int mesaSeleccionada;

    public AgregarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_agregar,container,false);

        verMenu = (Button) root.findViewById(R.id.btn_mostrar_menu);
        verAlmuerzo = (Button) root.findViewById(R.id.btn_mostrar_almuerzo);
        verPostre = (Button) root.findViewById(R.id.btn_mostrar_postre);
        finalizar = (Button) root.findViewById(R.id.btn_finalizar);
        spinner = (Spinner) root.findViewById(R.id.spinner_mesas);
        //Indicamos el contexto
        Firebase.setAndroidContext(getContext());
        //Manejo de los datos Offline
        firebase = new Firebase(FKeys.FIREBASE_URL).child(FKeys.FIREBASE_CHILD);

        verMenu.setOnClickListener(this);
        verAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence almuerzos[] = new CharSequence[] {"Arroz con hamburguesa",
                        "Bife a lo pobre",
                        "Cazuela de pavo",
                        "Ensalada cesar + nuggets de pollo"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Escoger Almuerzo");
                builder.setIcon(R.drawable.ic_dialog_break);
                builder.setItems(almuerzos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (validarMesa()){
                            mesaSeleccionada = spinner.getSelectedItemPosition();
                            Pedido pedido = new Pedido(almuerzos[which].toString(),mesaSeleccionada);
                            firebase.push().setValue(pedido);
                            Toast.makeText(getContext(), "Pedido agregado exitosamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Selecciona la Mesa antes de realizar pedido", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.show();
            }
        });
        verPostre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence postres[] = new CharSequence[] {"Suspiro Limeño",
                        "Tarta de frambuesa",
                        "Helado mix sabores",
                        "Mix de frutas)"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Escoger Almuerzo");
                builder.setIcon(R.drawable.ic_dialog_break);
                builder.setItems(postres, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (validarMesa()){
                            mesaSeleccionada = spinner.getSelectedItemPosition();
                            Pedido pedido = new Pedido(postres[which].toString(),mesaSeleccionada);
                            firebase.push().setValue(pedido);
                            Toast.makeText(getContext(), "Pedido agregado exitosamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Selecciona la Mesa antes de realizar pedido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return root;
    }

    @Override
    public void onClick(View v) {
        final CharSequence desayunos[] = new CharSequence[] {"Café, Pan aliado, Galletas surtidas",
                "Jugo natural, Trozo de torta, Surtido de frutas",
                "Capuccino, Trozo de Pie de limon, Galletas surtidas",
                "Mocaccino, Galletas surtidas, Trozo de torta, Cocadas de trufa"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Escoger Desayuno");
        builder.setIcon(R.drawable.ic_dialog_break);
        builder.setItems(desayunos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (validarMesa()){
                    mesaSeleccionada = spinner.getSelectedItemPosition();
                    Pedido pedido = new Pedido(desayunos[which].toString(),mesaSeleccionada);
                    firebase.push().setValue(pedido);
                    Toast.makeText(getContext(), "Pedido agregado exitosamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Selecciona la Mesa antes de realizar pedido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

    public boolean validarMesa(){
        if (spinner.getSelectedItemPosition() == 0){
            return false;
        }else{
            return true;
        }
    }
}
