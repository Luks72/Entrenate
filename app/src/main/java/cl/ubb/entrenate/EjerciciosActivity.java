package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import cl.ubb.entrenate.entidades.Clasificacion;
import cl.ubb.entrenate.utilidades.Utilidades;

public class EjerciciosActivity extends AppCompatActivity {

    AdminSQLiteAdminHelper db;

    Button agregar;
    EditText nombre, descripcion;

    ArrayList<String> listClasificacion, listEjercicios;
    ArrayAdapter adapterClasificacion, adapterEjercicios;

    ListView listViewClasificacion, listViewEjercicios;
    Spinner spinnerClasifiacacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);

        db = new AdminSQLiteAdminHelper(this, "bdd_clasificacion", null,1);


        listClasificacion = new ArrayList<>();
        listEjercicios = new ArrayList<>();

        agregar= findViewById(R.id.btn_ejercicios_agregar);
        nombre= findViewById(R.id.txt_ejercicios_nombre);
        descripcion= findViewById(R.id.txt_ejercicios_descripcion);
        listViewClasificacion= findViewById(R.id.list_clasificacion_ejercicios);
        spinnerClasifiacacion = findViewById(R.id.spn_clasificacion_ejercicios);

        listClasificacion();
        spinnerClasifiacacion();

    }

    public void onClick (View view) {
        int position = spinnerClasifiacacion.getSelectedItemPosition();
        Clasificacion clas = (Clasificacion) spinnerClasifiacacion.getAdapter().getItem(position);
        String name=nombre.getText().toString();
        String desc=descripcion.getText().toString();
        db.agregar_ejercicios(name, desc, clas);
        nombre.setText("");
        descripcion.setText("");
        Toast.makeText(EjerciciosActivity.this, "Agregado", Toast.LENGTH_SHORT).show();

    }

    public void listClasificacion() {
        Cursor cursor = db.ver_clasificacion();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay clasificacion", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listClasificacion.add(cursor.getString(1));
            }
            adapterClasificacion = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listClasificacion);
            listViewClasificacion.setAdapter(adapterClasificacion);
        }
    }

    public void spinnerClasifiacacion() {
        Cursor cursor = db.ver_clasificacion();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay ejercicios", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listEjercicios.add(cursor.getString(1));
            }
            adapterEjercicios = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listEjercicios);
            spinnerClasifiacacion.setAdapter(adapterEjercicios);
        }
    }


}