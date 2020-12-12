package cl.ubb.entrenate.ui.ejercicios;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;

import cl.ubb.entrenate.AdminSQLiteAdminHelper;
import cl.ubb.entrenate.ClasificacionActivity;
import cl.ubb.entrenate.DetalleEjercicio;
import cl.ubb.entrenate.MainActivity;
import cl.ubb.entrenate.MainMenu;
import cl.ubb.entrenate.R;
import cl.ubb.entrenate.adaptadores.ImagenesAdaptador;
import cl.ubb.entrenate.entidades.Ejercicios;

public class EjerciciosFragment extends Fragment implements Serializable{

    AdminSQLiteAdminHelper db;
    private GridView gridView;
    private EjerciciosViewModel ejerciciosViewModel;
    private ImagenesAdaptador adaptador;
    SwipeRefreshLayout refreshLayout;
    private ArrayList<Ejercicios> ejercicios;
    private TextView vacio;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ejerciciosViewModel =  new ViewModelProvider(this).get(EjerciciosViewModel.class);

        View root = inflater.inflate(R.layout.fragment_ejercicios, container, false);

        db = new AdminSQLiteAdminHelper(getActivity(), "entrenate_bdd", null,1);

        ejercicios= new ArrayList<>();
        gridView = (GridView) root.findViewById(R.id.grid);
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh);
        vacio = (TextView) root.findViewById(R.id.vacio);
        vacio.setVisibility(View.VISIBLE);
        gridView.setEmptyView(vacio);

        rellenarGrid();


         //**cambiar ícono del botón flotante**

        /*FloatingActionButton floatingActionButton = ((MainMenu) getActivity()).getFab();
        floatingActionButton.setImageResource(R.drawable.ic_baseline_add_24);
        floatingActionButton.show();*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ejercicios selected = ejercicios.get(position);
                String nombre = selected.getNombre();
                String descripcion = selected.getDescripción();
                byte[] imagen = selected.getImagen();
                int ide = selected.getId();
                String video = selected.getVideo();
                startActivity(new Intent(getActivity(), DetalleEjercicio.class)
                        .putExtra("nombre", nombre)
                        .putExtra("descripcion", descripcion)
                        .putExtra("imagen", imagen)
                        .putExtra("id", ide)
                        .putExtra("video", video));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager()
                        .beginTransaction()
                        .detach(EjerciciosFragment.this)
                        .attach(EjerciciosFragment.this)
                        .commit();
            }
        });

        return root;
    }


    private void rellenarGrid() {
        Cursor cursor = db.ver_ejercicios();
        if (cursor.getCount()== 0 ){
            Toast.makeText(getActivity(), "No hay ejercicios para mostrar, puedes agregar presionando en el botón +", Toast.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String desc = cursor.getString(2);
                byte[] image = cursor.getBlob(3);
                String video = cursor.getString(4);

                ejercicios.add(new Ejercicios(id, nombre, desc, image, video));

            }
            adaptador = new ImagenesAdaptador(getActivity(), ejercicios);
            gridView.setAdapter(adaptador);
        }
    }



}