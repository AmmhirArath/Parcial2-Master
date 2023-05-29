package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcial2.Helpers.EventosAdapter;
import com.example.parcial2.Models.Eventos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AtenderEventosActivity extends AppCompatActivity {

    ListView lstEventos;
    EventosAdapter adapterEventos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atender_eventos);
        this.InicializarControles();

        List<Eventos> eventos = EventosFileToList();
        adapterEventos = new EventosAdapter(getApplicationContext(),eventos);
        lstEventos.setAdapter(adapterEventos);
        this.Asistir();

    }
    private void InicializarControles() {
        lstEventos = (ListView) findViewById(R.id.lstEventos);
    }

    private List<Eventos> EventosFileToList() {
        List<Eventos> eventoList = new ArrayList<>();

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput("eventos.txt")));
            String datos = bf.readLine();

            String[] arrayEventos = datos.split("~");


            for (String partesEventos : arrayEventos){
                String[] camposEventos = partesEventos.split("\\|");

                Eventos evento = new Eventos(
                        camposEventos[0],
                        camposEventos[1],
                        camposEventos[2],
                        camposEventos[3],
                        camposEventos[4] );

                eventoList.add(evento);

            }


        } catch (Exception e){
            this.Notify("Algo salio mal! " + e.getMessage());
        }

        return eventoList;
    }

    public void Asistir(){
        lstEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Eventos eventoSeleccionado = (Eventos) parent.getItemAtPosition(position);
                NuevaAsistenciaEvento(view, position);
                Notify("El evento ha sido seleccionado!");
            }
        });

    }

    public void NuevaAsistenciaEvento(View view, int position){
        try {

            // Crear array para asistentes y cargar datos.

            String asistentes = "";
            String eventoActual = adapterEventos.getItem(position).getNombre();

            // Cargar datos actuales del usuario loggeado.

            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput("eventos.txt")));
            String datos = bf.readLine();

            String[] arrayEventos = datos.split("~");

            SharedPreferences user = getSharedPreferences("loginActual", Context.MODE_PRIVATE);
            String usuarioActual = user.getString("nombre","");

            // Recorrer array.

            for (String partesEventos : arrayEventos){
                String[] camposEventos = partesEventos.split("\\|");
                String eventoNombreRecorrido = camposEventos[0];

                if(eventoNombreRecorrido.equals(eventoActual)){



                    String fileName = "asistentes_" + eventoActual.replaceAll("\\s+","_") + ".txt";
                    boolean existe = VerificarArchivoAsistencia(fileName);

                    if(existe){
                        BufferedReader bfA = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
                        String texto = bfA.readLine();
                        bf.close();

                        asistentes = texto + usuarioActual + "/";

                        OutputStreamWriter out = new OutputStreamWriter(
                                openFileOutput(fileName, Context.MODE_PRIVATE));
                        out.write(asistentes);
                        out.close();
                    } else {
                        OutputStreamWriter out = new OutputStreamWriter(
                                openFileOutput(fileName, Context.MODE_PRIVATE));
                        asistentes = usuarioActual + "/";
                        out.write(asistentes);
                        out.close();
                    }
                    this.Notify("Ha sido agregado al evento!");
                }

            }

        } catch (Exception e){
            this.Notify("Algo salio mal en el proceso. " + e.getMessage());
        }
    }

    public void Notify(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public boolean VerificarArchivoAsistencia(String fileName){
        try
        {
            BufferedReader bf = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
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


}