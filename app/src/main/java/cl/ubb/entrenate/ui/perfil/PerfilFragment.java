package cl.ubb.entrenate.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavType;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cl.ubb.entrenate.AgregarEjerciciosActivity;
import cl.ubb.entrenate.AgregarUsuario;
import cl.ubb.entrenate.DetalleEjercicio;
import cl.ubb.entrenate.DetalleRutina;
import cl.ubb.entrenate.R;
import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.entidades.Rutina;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    TextView txt_correo, txt_creacion, txt_rutinaActual;
    FirebaseFirestore bdd;
    ArrayList<String> correos, nombreRutinas;
    Date creacion;
    String s;
    Button btn_rutinaActual;
    ListView lista;
    ArrayAdapter adapter;

    ArrayList<Rutina> rutinas;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.perfil_fragment, container, false);
        bdd=FirebaseFirestore.getInstance();

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");

        correos= new ArrayList<>();
        rutinas= new ArrayList<>();
        nombreRutinas= new ArrayList<>();

        SharedPreferences prefs = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        txt_correo = (TextView) root.findViewById(R.id.txt_perfil_correo);
        txt_creacion = (TextView) root.findViewById(R.id.txt_perfil_dias);
        txt_rutinaActual = (TextView) root.findViewById(R.id.txt_perfil_rutinaActual);
        //btn_rutinaActual = (Button) root.findViewById(R.id.btn_perfil_crear);
        lista = root.findViewById(R.id.list_perfil_rutinas);
        consultarRutina(correo);

        bdd.collection("usuarios")
                .whereEqualTo("correo", correo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   correos.clear();
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()){
                                                       correos.add(documentSnapshots.getString("correo"));
                                                       creacion = documentSnapshots.getDate("creacion");
                                                       txt_rutinaActual.setText(documentSnapshots.getString("rutinaActual"));
                                                   }
                                                   s= formatter.format(creacion);
                                                   txt_creacion.setText(s);
                                               }
                                           }
                                       }

                );

        /*btn_rutinaActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DetalleRutina.class));
            }
        });*/

        bdd.collection("usuarios").document(correo).collection("rutinaActual")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                nombreRutinas.add(documentSnapshot.getString("rutinaActual"));
                                Log.e("rutinas", "rutinas agregadas al perfil");
                                DocumentReference documentReference = bdd.collection("rutinas").document(documentSnapshot.getString("rutinaActual"));
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                        if (task.isComplete()) {
                                            DocumentSnapshot document = task1.getResult();
                                            ArrayList<String> arrayList = (ArrayList<String>) document.get("ejercicios");
                                            Log.e("Listado ejercicios", ""+arrayList);
                                            //rutinas.add(document.get);
                                            /*for (String s : nombreRutinas) {
                                                bdd.collection("rutinas")
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                   @Override
                                                                                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                       if (task.isSuccessful()) {
                                                                                           for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                                                                               bdd.collection("clasificacion").document(documentSnapshots.getString("nombre")).collection("ejercicios")
                                                                                                       .whereEqualTo("nombre", s)
                                                                                                       .get()
                                                                                                       .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                                                  @Override
                                                                                                                                  public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                                                                                                      if (task1.isSuccessful()) {
                                                                                                                                          for (QueryDocumentSnapshot documentSnapshots1 : task1.getResult()) {
                                                                                                                                              listItem.add(documentSnapshots1.getString("nombre"));
                                                                                                                                              ejercicios.add(documentSnapshots1.toObject(Ejercicios.class));

                                                                                                                                          }
                                                                                                                                          adapter = new ArrayAdapter(DetalleRutina.this, android.R.layout.simple_list_item_1, listItem);
                                                                                                                                          lista.setAdapter(adapter);
                                                                                                                                      }
                                                                                                                                  }
                                                                                                                              }

                                                                                                       );
                                                                                           }

                                                                                       }
                                                                                   }
                                                                               }

                                                        );
                                            }*/
                                        }else {
                                            Toast.makeText(getActivity(), "No hay ejercicios agregados", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                     Log.e("AA0", "algo salió mal");
                                    }
                                });

                            }
                            adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, nombreRutinas);
                            lista.setAdapter(adapter);

                        }
                    }
                });


        txt_correo.setText(correo);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

    public void consultarRutina(String correo){
        bdd.collection("usuarios").document(correo).collection("rutinaActual")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                String nombre_rutina = documentSnapshots.getString("rutinaActual");
                            }

                        }
                    }

                });
    }

}