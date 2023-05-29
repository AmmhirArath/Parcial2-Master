package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcial2.Helpers.HistorialAdapter;
import com.example.parcial2.Models.Eventos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HistorialEventos extends AppCompatActivity {

    TextView txtAsistentes;
    ListView lstHistorial;
    HistorialAdapter historialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencias_eventos);
        this.InicializarControles();


    }

    private void InicializarControles() {
        txtAsistentes = (TextView)findViewById(R.id.txtAsistentes);
        lstHistorial = (ListView)findViewById(R.id.lstHistorial);

        List<Eventos> historial = HistorialFileToList();
        historialAdapter = new HistorialAdapter(getApplicationContext(),historial);
        lstHistorial.setAdapter(historialAdapter);

    }

    private List<Eventos> HistorialFileToList() {
        List<Eventos> historialList = new ArrayList<>();

        try {

            BufferedReader bfEventos = new BufferedReader(new InputStreamReader(openFileInput("eventos.txt")));

            // Almacenamiento de datos en cada String.
            String datosEventos = bfEventos.readLine();


            String[] arrayEventos = datosEventos.split("~");


            for (String partesEventos : arrayEventos){
                String[] camposEventos = partesEventos.split("\\|");

                String fileAsistencia = "asistentes_" + camposEventos[0].replaceAll("\\s+","_") + ".txt";
                BufferedReader bfAsistentes = new BufferedReader(new InputStreamReader(openFileInput(fileAsistencia)));
                String datosAsistentesRaw = bfAsistentes.readLine();

                String[] arrayTemp = datosAsistentesRaw.split("/");
                String datosAsistentes = "";

                for (int i = 0; i < arrayTemp.length; i++) {
                    datosAsistentes += "•" + arrayTemp[i] + "\n";
                }

                Eventos evento = new Eventos(
                        camposEventos[0],
                        camposEventos[1],
                        camposEventos[2],
                        camposEventos[3],
                        datosAsistentes);

                historialList.add(evento);

            }


        } catch (Exception e){
            this.Notify("Algo salio mal! " + e.getMessage());
        }

        return historialList;
    }



    public void Notify(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

}