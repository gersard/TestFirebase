package com.example.gerardo.testfirebase.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * A placeholder fragment containing a simple view.
 */
public class InicioActivityFragment extends Fragment {

    RecyclerView recyclerView;
    static FirebaseRecyclerAdapter adapter;
    private Firebase firebase;

    public InicioActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_menus_inicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Firebase.setAndroidContext(getContext());
        firebase = new Firebase(FKeys.FIREBASE_URL).child(FKeys.FIREBASE_CHILD);

        Query query = firebase.orderByChild("mesa");

        adapter = new FirebaseRecyclerAdapter<Pedido, EliminarFragment.PedidoViewHolder>(Pedido.class, R.layout.view_pedido,
                EliminarFragment.PedidoViewHolder.class,
                firebase) {
            @Override
            protected void populateViewHolder(EliminarFragment.PedidoViewHolder pedidoViewHolder, Pedido pedido, int i) {
                String descripcionPedido = pedido.getMenu();
                String mesa = String.valueOf("Mesa: " + pedido.getMesa());

                pedidoViewHolder.pedido.setText(descripcionPedido);
                //Toast.makeText(getContext(), pedido.getMenu(), Toast.LENGTH_SHORT).show();
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






    public static class PedidoViewHolder extends RecyclerView.ViewHolder {

        TextView pedido, mesa;
        ImageView imgCancelado;

        public PedidoViewHolder(View itemView) {
            super(itemView);
            pedido = (TextView) itemView.findViewById(R.id.txt_pedido);
            mesa = (TextView) itemView.findViewById(R.id.txt_mesa);

        }
    }

}
