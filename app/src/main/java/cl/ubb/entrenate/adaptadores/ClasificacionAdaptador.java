package cl.ubb.entrenate.adaptadores;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cl.ubb.entrenate.R;
import cl.ubb.entrenate.entidades.Clasificacion;

public class ClasificacionAdaptador extends BaseAdapter {
    private Context context;
    private ArrayList<Clasificacion> clasificacions;
    private ArrayList<String> nombres;


    public ClasificacionAdaptador(Context context, ArrayList<Clasificacion> clasificacions) {
        this.context = context;
        this.clasificacions = clasificacions;
    }

    @Override
    public int getCount() {
        return clasificacions.size();
    }

    @Override
    public Object getItem(int position) {
        return clasificacions.get(position);
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
            convertView = layoutInflater.inflate(R.layout.clasificacion_grid, null);
        }

        holder.nombre = convertView.findViewById(R.id.nombre_clasificacion);
        Clasificacion ej = clasificacions.get(position);
        holder.nombre.setText(ej.getNombre());
        return convertView;
    }
}
