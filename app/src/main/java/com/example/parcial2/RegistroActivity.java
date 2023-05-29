package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class RegistroActivity extends AppCompatActivity {

    EditText txtNombre, txtCedula, txtUsuario, txtPassword;
    RadioGroup rbgTipoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        this.InicializarControles();
    }

    public void InicializarControles(){
        txtNombre = (EditText) findViewById(R.id.txtEventoNombre);
        txtCedula = (EditText) findViewById(R.id.txtCedula);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        rbgTipoUser = (RadioGroup) findViewById(R.id.rbgTipoUser);
    }


    public void GuardarPersona(View view){
        try {

            // Agarrar el tipo de usuario seleccionado.
                int tipo = rbgTipoUser.getCheckedRadioButtonId();
                String tipoUser = "";
                if (tipo == R.id.rbtUsuario){
                    tipoUser = "usuario";
                } else if (tipo == R.id.rbtAdmin){
                    tipoUser = "admin";
                } else {
                    tipoUser = "usuario";
                }

            // Guardar los datos en un string.
                String guardar =
                                txtNombre.getText().toString() + "|" +
                                txtCedula.getText().toString() + "|" +
                                txtUsuario.getText().toString() + "|" +
                                txtPassword.getText().toString() + "|" +
                                tipoUser + "~";

//            boolean existeArchivo = VerificarExistenciaArchivo();

            int res = GuardarArchivo(guardar);
            if (res == 1){
                Notify("El usuario ha sido registrado.");
                startActivity(new Intent(this,MainActivity.class));
            } else {
                Notify("Ha ocurrido un error.");
            }


        } catch (Exception e){
            Notify("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public int GuardarArchivo(String guardar){
        try
        {
            boolean existeArchivo = VerificarExistenciaArchivo();
            if (existeArchivo){
               OverwriteArchivo(guardar);
            } else {
                OutputStreamWriter out = new OutputStreamWriter(
                        openFileOutput("creds.txt",Context.MODE_PRIVATE));
                out.write(guardar);
                out.close();
            }
            return 1;
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
        return 0;
    }

    private void Notify(String mensajito){
        Toast.makeText(this, mensajito, Toast.LENGTH_SHORT).show();
    }


    public boolean VerificarExistenciaArchivo(){
        try
        {
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput("creds.txt")));
            String texto = bf.readLine();
            bf.close();

            if (!texto.isEmpty())
                return true;

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
            return false;
        }
        return false;
    }

    // Si existe, se sobre escribe para *agregar* lo nuevo a lo viejo.
    public void OverwriteArchivo(String texto){
        try {
            BufferedReader bf =
                    new BufferedReader(new InputStreamReader(
                            openFileInput("creds.txt")));
            String old = bf.readLine();

            OutputStreamWriter out = new OutputStreamWriter(
                    openFileOutput("creds.txt",Context.MODE_PRIVATE));

            out.write(old + texto);
            out.close();


        }catch (Exception e){
            Toast.makeText(this,"Errorcito => "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    //EVENTOS
    
    

}