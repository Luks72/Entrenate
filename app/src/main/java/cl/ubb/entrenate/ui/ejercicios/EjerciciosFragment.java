package cl.ubb.entrenate.ui.ejercicios;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import cl.ubb.entrenate.AdminSQLiteAdminHelper;
import cl.ubb.entrenate.R;
import cl.ubb.entrenate.adaptadores.ImagenesAdaptador;
import cl.ubb.entrenate.entidades.Ejercicios;

public class EjerciciosFragment extends Fragment {

    AdminSQLiteAdminHelper db;
    private GridView gridView;
    private EjerciciosViewModel ejerciciosViewModel;
    private ImagenesAdaptador adaptador;
    private ArrayList<Bitmap> bitmaps;
    private ArrayList<String> nombres;
    ArrayAdapter adapter;

    private ArrayList<Ejercicios> ejercicios;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ejerciciosViewModel =  new ViewModelProvider(this).get(EjerciciosViewModel.class);

        View root = inflater.inflate(R.layout.fragment_ejercicios, container, false);

        db = new AdminSQLiteAdminHelper(getActivity(), "entrenate_bdd", null,1);
        bitmaps = new ArrayList<Bitmap>();
        nombres = new ArrayList<String>();
        ejercicios= new ArrayList<>();

        gridView = (GridView) root.findViewById(R.id.grid);

        verImagenes();
        verNombres();
         //**cambiar ícono del botón flotante**

        /*FloatingActionButton floatingActionButton = ((MainMenu) getActivity()).getFab();
        floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
        floatingActionButton.show();*/



        return root;
    }

    private void verImagenes() {
        Cursor cursor = db.getImagen();
        if (cursor.getCount()== 0 ){
            Toast.makeText(getActivity(), "No hay ná", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                byte[] image = cursor.getBlob(3);
                Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
                bitmaps.add(bmp);
            }
            //adaptador = new ImagenesAdaptador(getActivity(), bitmaps);
            //gridView.setAdapter(adaptador);
        }
    }

    private void verNombres() {
        Cursor cursor = db.ver_ejercicios();
        if (cursor.getCount()== 0 ){
            Toast.makeText(getActivity(), "No hay ná", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String desc = cursor.getString(2);
                byte[] image = cursor.getBlob(3);

                ejercicios.add(new Ejercicios(id, nombre, desc, image));

            }
            adaptador = new ImagenesAdaptador(getActivity(), ejercicios);
            gridView.setAdapter(adaptador);
        }
    }


}