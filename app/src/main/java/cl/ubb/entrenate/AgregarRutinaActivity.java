package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import  android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
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
   EditText nombre_rutina, desc_rutina, vxs_rutina;
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
        setTitle("Agregar rutinas");
        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);

        ejercicios= new ArrayList<>();

        bdd=FirebaseFirestore.getInstance();
        desc_rutina = findViewById(R.id.itxt_agregarRutina_desc);
        vxs_rutina= findViewById(R.id.inumber_agregarRutina_vecesxsemana);
        nombre_rutina= findViewById(R.id.itxt_agregarRutina_nombre);
        btn_aceptar= findViewById(R.id.btn_agregarRutina_aceptar);
        btn_cancelar= findViewById(R.id.btn_agregarRutina_cancelar);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String nombre = nombre_rutina.getText().toString();
                String desc = desc_rutina.getText().toString();
                String vxs = vxs_rutina.getText().toString();
                int i = Integer.parseInt(vxs);
                db.agregar_rutina(nombre, desc, i);
                nombre_rutina.setText("");
                desc_rutina.setText("");
                vxs_rutina.setText("");*/
                //Toast.makeText(AgregarRutinaActivity.this, "Rutina agregada", Toast.LENGTH_SHORT).show();
                if(!nombre_rutina.getText().toString().isEmpty()
                        && !desc_rutina.getText().toString().isEmpty()
                            && !vxs_rutina.getText().toString().isEmpty())
                {
                    Map<String, Object> data = new HashMap<>();
                    data.put("nombre", nombre_rutina.getText().toString());
                    data.put("descripcion", desc_rutina.getText().toString());
                    data.put("vxs", vxs_rutina.getText().toString());
                    bdd.collection("rutinas").document(nombre_rutina.getText().toString()).set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AgregarRutinaActivity.this, "Agregado exitosamente", Toast.LENGTH_SHORT).show();
                            nombre_rutina.setText("");
                            desc_rutina.setText("");
                            vxs_rutina.setText("");
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
                    Toast.makeText(AgregarRutinaActivity.this, "El campo no puede estar vacío", Toast.LENGTH_SHORT).show();
                }


                finish();
            }
        });
        //gridView = (GridView) findViewById(R.id.gv_agregarRutina_ejercicios);
        /*vacio = (TextView) findViewById(R.id.vacio_agregarrutina);
        vacio.setVisibility(View.VISIBLE);
        array = new ListadoEjercicios();
        gridView.setEmptyView(vacio);*/
        // btn_ejercicios= findViewById(R.id.btn_agregarrutina_ejercicios);
        //fragment=findViewById(R.id.fragment3);


    }

   /* @Override
    public void onResume(){
        super.onResume();
       btn_ejercicios.setVisibility(View.VISIBLE);
       btn_aceptar.setVisibility(View.VISIBLE);
       nombre.setVisibility(View.VISIBLE);
       fragment.setVisibility(View.INVISIBLE);

    }*/

    public void onClickCancelar(View view){
        finish();
    }

    /*@Override
    public void onClick(View view) {
        String nombre = nombre_rutina.getText().toString();
        String desc = desc_rutina.getText().toString();
        String vxs = vxs_rutina.getText().toString();
        int i = Integer.parseInt(vxs);
        db.agregar_rutina(nombre, desc, i);
        nombre_rutina.setText("");
        desc_rutina.setText("");
        vxs_rutina.setText("");
        Toast.makeText(AgregarRutinaActivity.this, "Rutina agregada", Toast.LENGTH_SHORT).show();






        /*Intent miIntent=null;
        miIntent= new Intent(AgregarRutinaActivity.this, ListadoEjercicios.class);
        startActivity(miIntent);*/

        /*Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_agregarrutina_ejercicios:
                androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment3, new EjerciciosFragment());
                fragmentTransaction.commit();
                btn_ejercicios.setVisibility(View.GONE);
                nombre.setVisibility(View.GONE);
                btn_aceptar.setVisibility(View.GONE)
                ;
                break;

        }*/




   /* private void rellenarGrid() {
        ArrayList <String> n = array.verListado();
        if (n.size()==0) {
            Toast.makeText(AgregarRutinaActivity.this, "No hay ejercicios para mostrar", Toast.LENGTH_LONG).show();
        } else {
           for(int i=0; i<n.size(); i++) {
                int id=(int) getIntent().getIntExtra("id",-1);
                String nombre = (String) getIntent().getExtras().get("nombre");
                String video = (String) getIntent().getExtras().get("video");
                String desc = (String) getIntent().getExtras().get("descripcion");
                byte[] image = (byte[]) getIntent().getExtras().get("imagen");
                int id_clasificacion=(int) getIntent().getIntExtra("idClas",0);
                ejercicios.add(new Ejercicios(id, nombre, desc, image, video, id_clasificacion));
            }
            adaptador = new ListadoNombreEjerciciosAdaptador(AgregarRutinaActivity.this, ejercicios);
            gridView.setAdapter(adaptador);
        }*/
    }


