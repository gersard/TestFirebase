package com.example.gerardo.testfirebase.model;

/**
 * Created by Gerardo on 27-02-2016.
 */
public class Pedido {

    String menu;
    boolean cancelado;
    int mesa;

    public Pedido(String menu, int mesa) {
        this.menu = menu;
        this.mesa = mesa;
        this.cancelado = false;
    }

    public Pedido() {
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }
}
