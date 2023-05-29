package com.example.parcial2.Helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcial2.Models.Eventos;
import com.example.parcial2.R;

import java.util.List;

public class EventosAdapter extends ArrayAdapter<Eventos> {

        List<Eventos> opciones;

        public EventosAdapter(Context context, List<Eventos> datos){
            super(context, R.layout.listview_eventos, datos);
            opciones = datos;
        }


        public View getView(int position, View convertView, ViewGroup parent){

            View v = convertView;

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listview_eventos, parent, false);

            TextView txtEventoNombre = (TextView) item.findViewById(R.id.txtEventoNombre);
            txtEventoNombre.setText(opciones.get(position).getNombre());

            TextView txtEventoLugar = (TextView) item.findViewById(R.id.txtEventoLugar);
            txtEventoLugar.setText(opciones.get(position).getLugar());

            TextView txtEventoFechaHora = (TextView) item.findViewById(R.id.txtEventoFechaHora);
            txtEventoFechaHora.setText(opciones.get(position).getFecha() + " - " + opciones.get(position).getHora());


            return item;
        }

    }

