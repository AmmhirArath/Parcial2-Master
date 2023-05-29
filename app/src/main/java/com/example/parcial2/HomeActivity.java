package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView txtBienvenida;
    Button btnAsistenciaEvento, btnCrearEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.InicializarControles();

        SharedPreferences user = getSharedPreferences("loginActual", Context.MODE_PRIVATE);
        String tipo = user.getString("tipoUser","");
        String usuario = user.getString("user", "");
        String cedula = user.getString("cedula", "");
        String nombre = user.getString("nombre", "");

        txtBienvenida.setText("😎 " + txtBienvenida.getText().toString() + " " + usuario + " o AKA " + nombre + ", de cedula " + cedula + " y tipo de usuario " + tipo + "😎" );


        if(!tipo.equals("admin")){
            btnCrearEvento.setVisibility(View.GONE);
            btnAsistenciaEvento.setVisibility(View.GONE);
        }

    }

    public void InicializarControles(){
        txtBienvenida = (TextView) findViewById(R.id.txtBienvenida);
        btnAsistenciaEvento = (Button) findViewById(R.id.btnAsistenciaEvento);
        btnCrearEvento = (Button) findViewById(R.id.btnCrearEvento);
    }

    public void IrCrearEventos(View view){
        startActivity(new Intent(getApplicationContext(), CrearEventosActivity.class));
    }

    public void IrAtenderEventos(View view){
        startActivity(new Intent(getApplicationContext(), AtenderEventosActivity.class));
    }

    public void IrAsistenciasEventos(View view){
        startActivity(new Intent(getApplicationContext(), HistorialEventos.class));
    }

    public void Logout(View v){
        SharedPreferences login = getSharedPreferences("Login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = login.edit();

        editor.putString("name","");
        editor.putString("id","");
        editor.putInt("age",0);
        editor.putString("user","");
        editor.putString("password","");
        editor.putString("userType","");

        editor.commit();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    // Crear actividades para cada pantalla:
    // Crear Evento / Flier, Historial Eventos, Registrarse a Eventos, Asistencia de Evento

}