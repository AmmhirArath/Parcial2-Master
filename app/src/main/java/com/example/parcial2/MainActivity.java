package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputContentInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parcial2.Models.Usuarios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declaraciones de inputs del view.
    EditText txtUsuario, txtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.InicializarControles();
        //this.ValidarLogin();
    }

    public void InicializarControles(){
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContraseña = (EditText) findViewById(R.id.txtContraseña);
    }

    public void Login(View view){
        try {
            // Almacenar los valores de ambos controles.
            String inputUser = txtUsuario.getText().toString();
            String inputPass = txtContraseña.getText().toString();

            List<Usuarios> usuariosRegistrados = FileToList();

            boolean logged = false;

            for (Usuarios usuarios : usuariosRegistrados){

                if (inputUser.equals(usuarios.getUser()) &&
                        inputPass.equals(usuarios.getPassword())){

                    // SharedPrefs para guardar la sesión actual.
                    SharedPreferences user = getSharedPreferences("loginActual", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = user.edit();

                    editor.putString("nombre", usuarios.getNombre());
                    editor.putString("cedula", usuarios.getCedula());
                    editor.putString("user", usuarios.getUser());
                    editor.putString("pass", usuarios.getPassword());
                    editor.putString("tipoUser", usuarios.getTipoUser());

                    editor.commit();

                    // Toques finales e ida al main.
                    logged = true;

//                    // Pasar datos a otra actividad.
//                    Bundle datosBundle = new Usuarios().EmpacarDatosUsuario();
                    Intent i = new Intent(this, HomeActivity.class);
//                    i.putExtras(datosBundle);
                    startActivity(i);
                }
            }

            if(!logged){
                this.Notify("Datos incorrectos.");
            }



        } catch (Exception e){

        }
    }

//    private void ValidarLogin() {
//        SharedPreferences usuarioLogueado = getSharedPreferences("loginActual", Context.MODE_PRIVATE);
//        String usuario = usuarioLogueado.getString("user","");
//
//        // Poner método para hacer y llevar Bundle aqui.
//
//        if(!usuario.equals("")){
//            startActivity(new Intent(this, HomeActivity.class));
//        }
//    }

    private List<Usuarios> FileToList() {
        List<Usuarios> usuariosList = new ArrayList<>();

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput("creds.txt")));
            String datos = bf.readLine();

            String[] arrayUsuarios = datos.split("~");

            for (String partesUsuarios : arrayUsuarios){
                String[] camposUsuarios = partesUsuarios.split("\\|");

                Usuarios usuario = new Usuarios(
                        camposUsuarios[0],
                        camposUsuarios[1],
                        camposUsuarios[2],
                        camposUsuarios[3],
                        camposUsuarios[4]
                );

                usuariosList.add(usuario);

            }


        } catch (Exception e){
            this.Notify("Algo salio mal! " + e.getMessage());
        }

        return usuariosList;
    }


    // Logica de Botones
    public void RegistarPersona(View view){
        startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
    }


    public void Notify(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    

}