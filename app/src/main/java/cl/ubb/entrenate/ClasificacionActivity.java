package cl.ubb.entrenate;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClasificacionActivity extends AppCompatActivity {
    AdminSQLiteAdminHelper db;

    ListView listViewClasifiacion;

    Button agregar;
    EditText nombre;

    ArrayList <String> listItem;
    ArrayAdapter adapter;

    FirebaseFirestore bdd;
    CollectionReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);

        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);
        listItem = new ArrayList<>();

        bdd=FirebaseFirestore.getInstance();

        agregar= findViewById(R.id.btn_clasificacion_agregar);
        nombre= findViewById(R.id.txt_clasificacion_nombre);
        listViewClasifiacion= findViewById(R.id.list_clasificacion);


        viewData();

        listViewClasifiacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text= listViewClasifiacion.getItemAtPosition(position).toString();
                Toast.makeText(ClasificacionActivity.this, ""+text, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void viewData() {
        /*Cursor cursor = db.ver_clasificacion();
        if (cursor.getCount()== 0 ){
            Toast.makeText(this, "No hay clasificacion agregadas", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
            listViewClasifiacion.setAdapter(adapter);
        }*/

        bdd.collection("clasificacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            listItem.clear();
                            for (QueryDocumentSnapshot documentSnapshots: task.getResult()){
                                listItem.add(documentSnapshots.getString("nombre"));
                                Log.e("nombre", documentSnapshots.getString("nombre"));
                            }
                            adapter = new ArrayAdapter<String>(ClasificacionActivity.this, android.R.layout.simple_list_item_1, listItem);
                            adapter.notifyDataSetChanged();
                            listViewClasifiacion.setAdapter(adapter);
                        }
                    }
                }

                );

    }

    public void onClick (View view){
        if(!nombre.getText().toString().isEmpty()){
            Map<String, Object> data = new HashMap<>();
            data.put("nombre", nombre.getText().toString());
            bdd.collection("clasificacion").document(nombre.getText().toString()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ClasificacionActivity.this, "Agregado exitosamente", Toast.LENGTH_SHORT).show();
                    nombre.setText("");
                    adapter.notifyDataSetChanged();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ClasificacionActivity.this, "Ha ocurrido un problema", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ClasificacionActivity.this, "Vuelta a ingresar el nombre", Toast.LENGTH_SHORT).show();
                    nombre.setText("");
                }
            });
        }else{
            Toast.makeText(ClasificacionActivity.this, "El campo no puede estar vacío", Toast.LENGTH_SHORT).show();
        }





    }





}