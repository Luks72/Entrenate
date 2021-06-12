package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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
import com.google.firebase.firestore.SetOptions;

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
    String correoUsuario;
    FirebaseFirestore bdd;
    SharedPreferences prefs;
    CollectionReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);
        bdd=FirebaseFirestore.getInstance();
        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);
        listItem = new ArrayList<>();

        prefs = getSharedPreferences("credenciales", MODE_PRIVATE);
        correoUsuario = prefs.getString("correo", null);


        agregar= findViewById(R.id.btn_clasificacion_agregar);
        nombre= findViewById(R.id.txt_clasificacion_nombre);

        /*listViewClasifiacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text= listViewClasifiacion.getItemAtPosition(position).toString();
                Toast.makeText(ClasificacionActivity.this, ""+text, Toast.LENGTH_SHORT).show();

            }
        });*/

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().isEmpty()){
                    Map<String, Object> data = new HashMap<>();
                    data.put("nombre", nombre.getText().toString());
                    bdd.collection("preparador").document(correoUsuario).collection("clasificacion").document(nombre.getText().toString()).set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ClasificacionActivity.this, "Agregado exitosamente", Toast.LENGTH_SHORT).show();
                            nombre.setText("");
                            finish();
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
                    nombre.setError("El campo no puede estar vacío");
                }
            }
        });

    }

}