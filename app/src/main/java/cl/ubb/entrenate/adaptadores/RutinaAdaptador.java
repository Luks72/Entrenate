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

public class RutinaAdaptador extends BaseAdapter {
    private Context context;
    private ArrayList<Rutina> rutinas;
    private ArrayList<String> nombres;


    public RutinaAdaptador(Context context, ArrayList<Rutina> rutinas) {
        this.context = context;
        this.rutinas = rutinas;
    }

    @Override
    public int getCount() {
        return rutinas.size();
    }

    @Override
    public Object getItem(int position) {
        return rutinas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView nombre;
        TextView descripcion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.rutinas_grid, null);
        }

        holder.nombre = (TextView) convertView.findViewById(R.id.nombre_rutina);
        holder.descripcion = convertView.findViewById(R.id.descripcion_rutina);
        Rutina ej = rutinas.get(position);
        holder.nombre.setText(ej.getNombre());
        holder.descripcion.setText(ej.getDescripcion());
        return convertView;
    }
}