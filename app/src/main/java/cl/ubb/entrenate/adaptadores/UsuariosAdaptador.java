package cl.ubb.entrenate.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cl.ubb.entrenate.R;
import cl.ubb.entrenate.entidades.Usuario;

public class UsuariosAdaptador extends BaseAdapter {
    private Context context;
    private ArrayList<Usuario> usuarios;
    private ArrayList<String> nombres;


    public UsuariosAdaptador(Context context, ArrayList<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView nombre;
        TextView correo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.usuarios_grid, null);
        }

        holder.nombre = convertView.findViewById(R.id.nombre_usuario);
        holder.correo = convertView.findViewById(R.id.correo_usuario);
        Usuario ej = usuarios.get(position);
        holder.nombre.setText(ej.getNombre());
        holder.correo.setText(ej.getCorreo());
        return convertView;
    }
}

