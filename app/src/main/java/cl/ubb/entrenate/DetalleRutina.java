package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cl.ubb.entrenate.adaptadores.DetalleRutinaAdaptador;
import cl.ubb.entrenate.adaptadores.ImagenesAdaptador;
import cl.ubb.entrenate.entidades.DetalleEjercicioRutina;
import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.entidades.Rutina;

public class DetalleRutina extends AppCompatActivity {

    private TextView txt_nombre, txt_descripcion, txt_ejercicio_nombre;
    private AdminSQLiteAdminHelper db;
    EditText itxt_repeticiones;
    Button btn_agregarEjercicio, btn_editar, btn_eliminar, btn_asignar;
    ListView lista, lista_repeticiones;
    ArrayList<String> listItem, listEjercicios, nombres_listview, repeticiones, list_repeticiones, arr;
    ArrayAdapter adapter, adapterEjercicios, adapterRepeticiones;
    Spinner spinnerEjercicios;
    private ArrayList<Ejercicios> ejercicios;
    private GridView gridView;
    DetalleRutinaAdaptador detalleRutinaAdaptador;
    ArrayList<DetalleEjercicioRutina> detalleEjercicioRutinas;

    FirebaseFirestore bdd;
    Boolean result;
    DocumentReference rutinaActual, rutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_rutina);

        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);

        bdd= FirebaseFirestore.getInstance();

        ejercicios= new ArrayList<>();
        detalleEjercicioRutinas= new ArrayList<>();
        txt_descripcion = findViewById(R.id.txt_rutina_descripcion);
        btn_agregarEjercicio = findViewById(R.id.btn_agregar_detalleRutina);
        //btn_eliminar = findViewById(R.id.btn_detalleRutina_eliminar);
        //btn_editar = findViewById(R.id.btn_detalleRutina_editar);
        btn_asignar = findViewById(R.id.btn_detalleRutina_asignar);
        gridView = (GridView) findViewById(R.id.grid_ejericios);
        //lista = findViewById(R.id.list_detalleRutina);
        //lista_repeticiones = findViewById(R.id.list_detalleRutina_repeticiones);
        //spinnerEjercicios = findViewById(R.id.sp_detalleRutina);
        //itxt_repeticiones = findViewById(R.id.itxt_detalleRutina_repeticiones);

        listItem = new ArrayList<>();
        listEjercicios = new ArrayList<>();
        nombres_listview = new ArrayList<>();
        repeticiones = new ArrayList<>();
        arr = new ArrayList<>();

        AlertDialog alert = confirmar();
        ArrayList<String> nombre_ejercicio = new ArrayList<>();

        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);

        String nombre_rutina = (String) getIntent().getExtras().get("nombre");
        String descripcion_rutina = (String) getIntent().getExtras().get("descripcion");
        int id_ejercicio=(int) getIntent().getIntExtra("idEjercicio",0);
        int id_rutina=(int) getIntent().getIntExtra("id",0);
        haydatos(nombre_rutina);
        DocumentReference documentReference = bdd.collection("rutinas").document(nombre_rutina);
        rutinaActual = bdd.collection("usuarios").document(correo).collection("rutinaActual").document(nombre_rutina);

        setTitle(nombre_rutina);
        viewData(nombre_rutina);
        //spinner();


        btn_agregarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleRutina.this, AgregarEjercicioARutina.class)
                        .putExtra("nombreRutina", nombre_rutina);
                startActivity(intent);
            }
        });

        /*btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });*/

        /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ejercicios selected = ejercicios.get(position);
                String nombre = selected.getNombre();
                String descripcion = selected.getDescripcion();
                String url = selected.getUrl();
                int ide = selected.getId();
                String video = selected.getVideo();
                int id_clas=selected.getId_clasificacion();
                startActivity(new Intent(DetalleRutina.this, DetalleEjercicio.class)
                        .putExtra("nombre", nombre)
                        .putExtra("descripcion", descripcion)
                        .putExtra("url", url)
                        .putExtra("id", ide)
                        .putExtra("video", video)
                        .putExtra("idClas", id_clas));


            }
        });*/

        /*btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetalleRutina.this, EditarRutina.class)
                        .putExtra("id", id_rutina));
            }
        });*/



        /*rutinaActual.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(nombre_rutina.equalsIgnoreCase(task.getResult().getString("rutinaActual"))){
                        btn_asignar.setVisibility(View.GONE);
                    }else{
                        btn_asignar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rutinaActual.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                String descripcion = (String) getIntent().getExtras().get("descripcion");
                                                Map<String, Object> data = new HashMap<>();
                                                data.put("rutinaActual", nombre_rutina);
                                                rutinaActual.set(data, SetOptions.merge());
                                                startActivity(new Intent(DetalleRutina.this, AgregarRutinaActual.class)
                                                        .putExtra("nombreRutina", nombre_rutina)
                                                        .putExtra("descripcion", descripcion));
                                                Toast.makeText(DetalleRutina.this, "Rutina Asignada", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error deleting document", e);
                                            }
                                        });
                                }
                        });
                    }
                }
            }
        });*/

        txt_descripcion.setText(descripcion_rutina);


    }

    private void viewData(String nombre_rutina) {

        bdd.collection("rutinas").document(nombre_rutina).collection("ejercicios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            detalleEjercicioRutinas.clear();
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                detalleEjercicioRutinas.add(documentSnapshot.toObject(DetalleEjercicioRutina.class));

                            }
                            detalleRutinaAdaptador = new DetalleRutinaAdaptador(DetalleRutina.this, detalleEjercicioRutinas);
                            detalleRutinaAdaptador.notifyDataSetChanged();
                            gridView.setAdapter(detalleRutinaAdaptador);
                        }
                    }
                });

        /*Cursor cursor = db.buscar_ejercicioARutina2(id);
        if (cursor.getCount()== 0 ){
            Toast.makeText(this, "no hay ejercicios agregados", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                Cursor cursor2 = db.buscar_ejercicioARutina(cursor.getInt(0));
                while (cursor2.moveToNext()){
                    listItem.add(cursor2.getString(1));
                    int ide = cursor2.getInt(0);
                    String nombre = cursor2.getString(1);
                    String desc = cursor2.getString(2);
                    byte[] image = cursor2.getBlob(3);
                    String video = cursor2.getString(4);
                    int id_clasificacion = cursor2.getInt(5);
                    ejercicios.add(new Ejercicios(ide, nombre, desc, image, video, id_clasificacion));
                }
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
            lista.setAdapter(adapter);
        }*/

    }


    public void spinner() {
        /*Cursor cursor = db.ver_ejercicios();
        listEjercicios.add("Seleccione el ejercicio");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay ejercicios", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listEjercicios.add(cursor.getString(1));
            }

            adapterEjercicios = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listEjercicios){
                @Override
                public boolean isEnabled(int position){
                    if (position == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }
            };
            spinnerEjercicios.setAdapter(adapterEjercicios);

        }
         */
        bdd.collection("clasificacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()) {
                                                       listEjercicios.clear();
                                                       listEjercicios.add("Seleccione una ejercicio");
                                                       bdd.collection("clasificacion").document(documentSnapshots.getString("nombre")).collection("ejercicios")
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                          @Override
                                                                                          public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                                                              if (task1.isSuccessful()){
                                                                                                  for (QueryDocumentSnapshot documentSnapshots1: task1.getResult()) {
                                                                                                      listEjercicios.add(documentSnapshots1.getString("nombre"));
                                                                                                  }
                                                                                              }
                                                                                              adapterEjercicios = new ArrayAdapter<String>(DetalleRutina.this, android.R.layout.simple_list_item_1, listEjercicios){
                                                                                                  @Override
                                                                                                  public boolean isEnabled(int position){
                                                                                                      if (position == 0) {
                                                                                                          return false;
                                                                                                      } else {
                                                                                                          return true;
                                                                                                      }
                                                                                                  }
                                                                                              };
                                                                                              spinnerEjercicios.setAdapter(adapterEjercicios);
                                                                                          }
                                                                                      }

                                                               );

                                                   }

                                               }
                                           }
                                       }

                );

    }



    /*public void detalleEjercicio(long id){
        Cursor cursor = db.buscar_ejerciciosEspecifico(id);
        while (cursor.moveToNext()) {
            int ide =cursor.getInt(0);
            String nombre = cursor.getString(1);
            String descripcion = cursor.getString(2);
            byte[] imagen = cursor.getBlob(3);
            String video = cursor.getString(4);
            int id_clas=cursor.getInt(5);
            startActivity(new Intent(DetalleRutina.this, DetalleEjercicio.class)
                    .putExtra("nombre", nombre)
                    .putExtra("descripcion", descripcion)
                    .putExtra("imagen", imagen)
                    .putExtra("id", ide)
                    .putExtra("video", video)
                    .putExtra("idClas", id_clas));
        }


    }*/

    private AlertDialog confirmar() {
        int id_rutina = (int) getIntent().getExtras().get("id");
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Eliminar Rutina")
                .setMessage("¿Quieres eliminar esta rutina?")
                .setIcon(R.drawable.ic_baseline_delete_forever_24)

                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.eliminar_rutina(id_rutina);
                        Toast.makeText(DetalleRutina.this, "Eliminado", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }

                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    public Boolean haydatos(String rutina_nombre){
        DocumentReference docRef = bdd.collection("rutinas").document(rutina_nombre);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Object nombre = document.get("ejercicios");
                        if(nombre != null){
                            result = true;
                        }else{
                            result = false;
                        }
                    } else {
                        Log.e("TAG", "No such document");
                    }
                } else {
                    Log.e("TAG", "get failed with ", task.getException());
                }
            }
        });
        return result;
    }


}