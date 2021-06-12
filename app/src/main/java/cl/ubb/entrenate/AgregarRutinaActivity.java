package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import  android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import cl.ubb.entrenate.adaptadores.ListadoEjerciciosAdaptador;
import cl.ubb.entrenate.adaptadores.ListadoNombreEjerciciosAdaptador;
import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.ui.ejercicios.EjerciciosFragment;

public class AgregarRutinaActivity extends AppCompatActivity {

   Button btn_ejercicios, btn_aceptar, btn_cancelar;
   EditText nombre_rutina, desc_rutina, vxs_rutina, duracion_rutina;
   AdminSQLiteAdminHelper db;
   private GridView gridView;
   private TextView vacio;
   //View fragment;
   private ArrayList<Ejercicios> ejercicios;
   private ListadoNombreEjerciciosAdaptador adaptador;
   int i=0;
   ListadoEjercicios array;
   FirebaseFirestore bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rutina);
        setTitle("Agregar rutina");
        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);

        ejercicios= new ArrayList<>();

        bdd=FirebaseFirestore.getInstance();
        desc_rutina = findViewById(R.id.itxt_agregarRutina_desc);
        vxs_rutina= findViewById(R.id.inumber_agregarRutina_vecesxsemana);
        nombre_rutina= findViewById(R.id.itxt_agregarRutina_nombre);
        duracion_rutina= findViewById(R.id.inumber_agregarRutina_duracion);
        btn_aceptar= findViewById(R.id.btn_agregarRutina_aceptar);
        btn_cancelar= findViewById(R.id.btn_agregarRutina_cancelar);

        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombre_rutina.getText().toString().isEmpty()) {
                    if(!desc_rutina.getText().toString().isEmpty()){
                        if(!vxs_rutina.getText().toString().isEmpty()){
                            if(!duracion_rutina.getText().toString().isEmpty()){
                                Map<String, Object> data = new HashMap<>();
                                data.put("nombre", nombre_rutina.getText().toString());
                                data.put("descripcion", desc_rutina.getText().toString());
                                data.put("vxs", vxs_rutina.getText().toString());
                                data.put("duracion", duracion_rutina.getText().toString());
                                bdd.collection("preparador").document(correo)
                                        .collection("rutinas").document(nombre_rutina.getText().toString())
                                            .set(data, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(AgregarRutinaActivity.this, "Agregado exitosamente", Toast.LENGTH_SHORT).show();
                                                    nombre_rutina.setText("");
                                                    desc_rutina.setText("");
                                                    vxs_rutina.setText("");
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(AgregarRutinaActivity.this, "Ha ocurrido un problema", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(AgregarRutinaActivity.this, "Vuelta a ingresar el nombre", Toast.LENGTH_SHORT).show();
                                                    nombre_rutina.setText("");
                                                    desc_rutina.setText("");
                                                    vxs_rutina.setText("");
                                                }
                                            });
                            }else{
                                duracion_rutina.setError("el campo no puede estar vacío");
                            }
                        }else{
                            vxs_rutina.setError("el campo no puede estar vacío");
                        }
                    }else{
                        desc_rutina.setError("el campo no puede estar vacío");
                    }
                }else{
                    nombre_rutina.setError("el campo no puede estar vacío");
                }
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}


