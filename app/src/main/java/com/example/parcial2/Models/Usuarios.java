package com.example.parcial2.Models;

import android.os.Bundle;

public class Usuarios {

    private String nombre, cedula, user, password, tipoUser;

    public Usuarios(String nombre, String cedula, String user, String password, String tipoUser) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.user = user;
        this.password = password;
        this.tipoUser = tipoUser;
    }

    public Usuarios(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public Bundle EmpacarDatosUsuario() {
        Bundle b = new Bundle();
        b.putString("nombre", this.getNombre());
        b.putString("cedula", this.getCedula());
        b.putString("user", this.getUser());
        b.putString("password", this.getPassword());
        b.putString("tipoUser", this.getTipoUser());

        return  b;
    }

    public Usuarios RestaurarDatosUsuarios(Bundle b){
        return new Usuarios(
                b.getString("nombre"),
                b.getString("cedula"),
                b.getString("user"),
                b.getString("password"),
                b.getString("tipoUser")
        );
    }

}
