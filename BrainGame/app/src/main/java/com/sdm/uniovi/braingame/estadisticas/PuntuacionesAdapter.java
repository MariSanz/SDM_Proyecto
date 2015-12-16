package com.sdm.uniovi.braingame.estadisticas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;

import java.util.List;

/**
 * Created by luism_000 on 16/12/2015.
 */
public class PuntuacionesAdapter extends ArrayAdapter<Puntuacion> {

    private LayoutInflater mInflater;

    public PuntuacionesAdapter(Context context, List<Puntuacion> puntuaciones) {
        super(context, 0, puntuaciones);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = convertView;
        if(vista == null) {
            vista = mInflater.inflate(R.layout.estadisticas_item, parent, false);
        }

        Puntuacion puntuacion = getItem(position);

        TextView textViewUsuario = (TextView)vista.findViewById(R.id.TextViewUsuario);
        TextView textViewPuntuacion = (TextView)vista.findViewById(R.id.TextViewPuntuacion);

        textViewUsuario.setText(puntuacion.getUsuario());
        textViewPuntuacion.setText(Integer.toString(puntuacion.getPuntos()));

        return vista;
    }
}
