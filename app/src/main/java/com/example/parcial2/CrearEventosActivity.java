package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parcial2.Models.Eventos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class CrearEventosActivity extends AppCompatActivity {

    EditText txtEventoNombre, txtEventoLugar;
    DatePicker tpEventoFecha;
    TimePicker tpEventoHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_eventos);
        this.InicializarControles();
    }

    public void InicializarControles() {
        txtEventoNombre = (EditText) findViewById(R.id.txtEventoNombre);
        txtEventoLugar = (EditText) findViewById(R.id.txtEventoLugar);
        tpEventoFecha = (DatePicker) findViewById(R.id.tpEventoFecha);
        tpEventoHora = (TimePicker) findViewById(R.id.tpEventoHora);
    }

    private void Notify(String mensajito){
        Toast.makeText(this, mensajito, Toast.LENGTH_SHORT).show();
    }


    public void GuardarEvento(View view){
        try {
            //nombre, lugar, fecha y hora, participantes
            String participantes = "asistentes_" + txtEventoNombre.getText().toString().replaceAll("\\s+","_") + ".txt";

            // Guardar los datos en un string.
                String guardar =
                                txtEventoNombre.getText().toString() + "|" +
                                txtEventoLugar.getText().toString() + "|" +
                                tpEventoFecha.getDayOfMonth() + "/" + tpEventoFecha.getMonth() + "/" + tpEventoFecha.getYear() + "|" +
                                tpEventoHora.getHour() + ":" + tpEventoHora.getMinute() + "|" +
                                participantes + "~";

//            boolean existeArchivo = VerificarExistenciaArchivo();

            int res = GuardarArchivoEventos(guardar);
            if (res == 1){
                Notify("El evento ha sido registrado.");
                startActivity(new Intent(this,HomeActivity.class));
            } else {
                Notify("Ha ocurrido un error.");
            }


        } catch (Exception e){
            Notify("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public int GuardarArchivoEventos(String guardar){
        try
        {
            boolean existeArchivo = VerificarExistenciaArchivoEvento();
            if (existeArchivo){
               OverwriteArchivoEvento(guardar);
            } else {
                OutputStreamWriter out = new OutputStreamWriter(
                        openFileOutput("eventos.txt", Context.MODE_PRIVATE));
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

    public boolean VerificarExistenciaArchivoEvento(){
        try
        {
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput("eventos.txt")));
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

    public void OverwriteArchivoEvento(String texto){
        try {
            BufferedReader bf =
                    new BufferedReader(new InputStreamReader(
                            openFileInput("eventos.txt")));
            String old = bf.readLine();

            OutputStreamWriter out = new OutputStreamWriter(
                    openFileOutput("eventos.txt",Context.MODE_PRIVATE));

            out.write(old + texto);
            out.close();


        }catch (Exception e){
            Toast.makeText(this,"Errorcito => "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

//    private void validarEvento{
//
//          for (Eventos evento : eventos){
//
//
//                    SharedPreferences event =
//                            getSharedPreferences("Event", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor  = event.edit();
//                    editor.putString("n",usuario.getNombre());
//                    editor.putString("l",usuario.getLugar());
//                    editor.putString("f",usuario.getFecha());
//                    editor.putString("h",usuario.getHora());
//
//                    editor.commit();
//
//                    startActivity(new Intent(this,AtenderEventoActivity.class));
//
//            }
//    }


}