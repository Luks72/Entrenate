package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgregarEjercicioARutina extends AppCompatActivity {

    EditText series, repeticiones, descanso, instrucciones;
    Button agregar, cancelar;
    Spinner spinner;
    FirebaseFirestore bdd;
    ArrayAdapter adapterEjercicios;
    ArrayList<String> listEjercicios;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ejercicio_a_rutina);

        listEjercicios = new ArrayList<>();
        prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);

        series = findViewById(R.id.itxt_agregarEjercicioARutina_series);
        repeticiones = findViewById(R.id.itxt_agregarEjercicioARutina_repeticiones);
        descanso = findViewById(R.id.itxt_agregarEjercicioARutina_descanso);
        //instrucciones = findViewById(R.id.itxt_agregarEjercicioARutina_instrucciones);
        agregar = findViewById(R.id.btn_agregarEjercicioARutina_agregar);
        cancelar = findViewById(R.id.btn_agregarEjercicioARutina_cancelar);
        spinner = findViewById(R.id.spn_agregarEjercicioARutina);
        bdd= FirebaseFirestore.getInstance();
        spinner();

        String nombre_rutina = (String) getIntent().getExtras().get("nombreRutina");
        setTitle("Agregar ejercicios a "+nombre_rutina);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_ejercicio = spinner.getSelectedItem().toString();
                int pos = spinner.getSelectedItemPosition();
                if(pos!=0){
                    if(!series.getText().toString().isEmpty()){
                        if(!repeticiones.getText().toString().isEmpty()){
                            if(!descanso.getText().toString().isEmpty()){
                                Map<String, Object> data = new HashMap<>();
                                data.put("series",repeticiones.getText().toString());
                                data.put("repeticiones",repeticiones.getText().toString());
                                data.put("descanso",descanso.getText().toString());
                                //data.put("instrucciones",instrucciones.getText().toString());
                                data.put("nombre",nombre_ejercicio);
                                data.put("nombreRutina",nombre_rutina);

                                bdd.collection("preparador").document(correo)
                                    .collection("rutinas").document(nombre_rutina)
                                        .collection("ejercicios").document(nombre_ejercicio)
                                            .set(data, SetOptions.merge());
                                Toast.makeText(AgregarEjercicioARutina.this, "Ejercicio agregado", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                descanso.setError("El campo no puede estar vacío");
                            }
                        }else{
                            repeticiones.setError("El campo no puede estar vacío");
                        }
                    }else{
                        series.setError("El campo no puede estar vacío");
                    }
                }else{
                Toast.makeText(AgregarEjercicioARutina.this, "Debe seleccionar un ejercicio", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void spinner() {
        prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        bdd.collection("preparador").document(correo)
                .collection("clasificacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           if (task.isSuccessful()){
                               for (QueryDocumentSnapshot documentSnapshots: task.getResult()) {
                                   listEjercicios.clear();
                                   listEjercicios.add("Seleccione una ejercicio");
                                   bdd.collection("preparador").document(correo)
                                           .collection("clasificacion").document(documentSnapshots.getString("nombre"))
                                                .collection("ejercicios")
                                               .get()
                                               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                      @Override
                                                      public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                          if (task1.isSuccessful()){
                                                              for (QueryDocumentSnapshot documentSnapshots1: task1.getResult()) {
                                                                  listEjercicios.add(documentSnapshots1.getString("nombre"));
                                                              }
                                                          }
                                                          adapterEjercicios = new ArrayAdapter<String>(AgregarEjercicioARutina.this, android.R.layout.simple_list_item_1, listEjercicios){
                                                              @Override
                                                              public boolean isEnabled(int position){
                                                                  if (position == 0) {
                                                                      return false;
                                                                  } else {
                                                                      return true;
                                                                  }
                                                              }
                                                          };
                                                          spinner.setAdapter(adapterEjercicios);
                                                      }
                                                  }

                                               );

                               }

                           }
                       }
                   }

                );

    }
}