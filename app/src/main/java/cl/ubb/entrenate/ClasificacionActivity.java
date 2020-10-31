package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClasificacionActivity extends AppCompatActivity {
    AdminSQLiteAdminHelper db;

    ListView listViewClasifiacion;

    Button agregar;
    EditText nombre;

    ArrayList <String> listItem;
    ArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);

        db = new AdminSQLiteAdminHelper(this, "bdd_clasificacion", null,1);
        listItem = new ArrayList<>();



        agregar= findViewById(R.id.btn_clasificacion_agregar);
        nombre= findViewById(R.id.txt_clasificacion_nombre);
        listViewClasifiacion= findViewById(R.id.list_clasificacion);

        viewData();

    }

    private void viewData() {
        Cursor cursor = db.ver_clasificacion();
        if (cursor.getCount()== 0 ){
            Toast.makeText(this, "No hay ná", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
            listViewClasifiacion.setAdapter(adapter);
        }
    }

    public void onClick (View view){
        String name=nombre.getText().toString();
        db.agregar_clasificacion(name);
        nombre.setText(" ");
        Toast.makeText(ClasificacionActivity.this, "Agregado", Toast.LENGTH_SHORT).show();
    }
}