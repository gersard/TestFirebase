package com.example.gerardo.testfirebase.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.testfirebase.FKeys;
import com.example.gerardo.testfirebase.R;
import com.example.gerardo.testfirebase.model.Pedido;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EliminarFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    private ChildEventListener pedidoListener;
     static FirebaseRecyclerAdapter adapter;
    private Firebase firebase;
    Button btnEliminarCancel;

    public EliminarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_eliminar, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_menus);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnEliminarCancel = (Button) root.findViewById(R.id.btn_eliminar_finalizados);
        btnEliminarCancel.setOnClickListener(this);

        Firebase.setAndroidContext(getContext());
        firebase = new Firebase(FKeys.FIREBASE_URL).child(FKeys.FIREBASE_CHILD);
        adapter = new FirebaseRecyclerAdapter<Pedido, PedidoViewHolder>(Pedido.class, R.layout.view_pedido,
                PedidoViewHolder.class,
                firebase) {
            @Override
            public void populateViewHolder(PedidoViewHolder pedidoViewHolder, Pedido pedido, int i) {
                String descripcionPedido = pedido.getMenu();
                String mesa = String.valueOf("Mesa: " + pedido.getMesa());

                pedidoViewHolder.pedido.setText(descripcionPedido);
                pedidoViewHolder.mesa.setText(mesa);
                if (pedido.isCancelado()) {
                    pedidoViewHolder.imgCancelado.setVisibility(View.VISIBLE);
                } else {
                    pedidoViewHolder.imgCancelado.setVisibility(View.INVISIBLE);
                }
            }


        };
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return root;
    }

    @Override
    public void onClick(View v) {
        Query query = firebase.orderByChild("cancelado").equalTo("true");
    }


    public static class PedidoViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView pedido,mesa;
        ImageView imgCancelado;

        public PedidoViewHolder(View itemView) {
            super(itemView);
            pedido = (TextView) itemView.findViewById(R.id.txt_pedido);
            mesa = (TextView) itemView.findViewById(R.id.txt_mesa);
            imgCancelado = (ImageView) itemView.findViewById(R.id.image_cancelado);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            Firebase referencia = adapter.getRef(getAdapterPosition());
            referencia.removeValue();
            return true;
        }
    }

}
