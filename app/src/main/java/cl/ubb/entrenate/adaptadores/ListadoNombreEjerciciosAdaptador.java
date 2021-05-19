package cl.ubb.entrenate.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.ubb.entrenate.R;
import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.entidades.Rutina;

public class ListadoNombreEjerciciosAdaptador extends BaseAdapter {
    private Context context;
    private ArrayList<Ejercicios> ejercicios;
    private ArrayList<String> nombres;


    public ListadoNombreEjerciciosAdaptador(Context context, ArrayList<Ejercicios> ejercicios) {
        this.context = context;
        this.ejercicios = ejercicios;
    }

    @Override
    public int getCount() {
        return ejercicios.size();
    }

    @Override
    public Object getItem(int position) {
        return ejercicios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView nombre;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listadoejercicios_grid, null);
        }

        holder.nombre = (TextView) convertView.findViewById(R.id.nombre_listadoejercicios);
        Ejercicios ej = ejercicios.get(position);
        holder.nombre.setText(ej.getNombre());


        return convertView;
    }
}