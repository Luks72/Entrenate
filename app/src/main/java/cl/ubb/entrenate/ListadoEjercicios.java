package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//import cl.ubb.entrenate.adaptadores.ListadoEjerciciosAdaptador;
import cl.ubb.entrenate.adaptadores.ListadoNombreEjerciciosAdaptador;
import cl.ubb.entrenate.adaptadores.RutinaAdaptador;
import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.entidades.Rutina;

public class ListadoEjercicios extends AppCompatActivity {

    AdminSQLiteAdminHelper db;
    private ListadoNombreEjerciciosAdaptador adaptador;
    private ArrayList<Ejercicios> ejercicios;

    private GridView gridView;
    private TextView vacio;
    public ArrayList<String> nombres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ejercicios);
        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null, 1);
        ejercicios = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.grid_listado);
        vacio = (TextView) findViewById(R.id.vacio_listado);
        vacio.setVisibility(View.VISIBLE);
        gridView.setEmptyView(vacio);
        //rellenarGrid();
        setTitle("Nombre ejercicios para agregar a rutina");

        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Ejercicios selected = ejercicios.get(position);
                String nombre = selected.getNombre();
                String descripcion = selected.getDescripción();
                byte[] imagen = selected.getImagen();
                int ide = selected.getId();
                String video = selected.getVideo();
                int id_clas = selected.getId_clasificacion();
                startActivity(new Intent(ListadoEjercicios.this, AgregarRutinaActivity.class)
                        .putExtra("nombre", nombre)
                        .putExtra("descripcion", descripcion)
                        .putExtra("imagen", imagen)
                        .putExtra("id", ide)
                        .putExtra("video", video)
                        .putExtra("idClas", id_clas));

                nombres.add(nombre);
            }
        });*/
    }


   /* private void rellenarGrid() {
        Cursor cursor = db.ver_ejercicios();
        if (cursor.getCount() == 0) {
            Toast.makeText(ListadoEjercicios.this, "No hay ejercicios para mostrar, puedes agregar presionando en el botón +", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String desc = cursor.getString(2);
                byte[] image = cursor.getBlob(3);
                String video = cursor.getString(4);
                int id_clasificacion = cursor.getInt(5);
                ejercicios.add(new Ejercicios(id, nombre, desc, image, video, id_clasificacion));
            }
            adaptador = new ListadoNombreEjerciciosAdaptador(ListadoEjercicios.this, ejercicios);
            gridView.setAdapter(adaptador);
        }

    }*/

    public ArrayList<String> verListado() {
        return (nombres);
    }

}
