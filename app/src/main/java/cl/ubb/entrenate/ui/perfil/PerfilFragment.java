package cl.ubb.entrenate.ui.perfil;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavType;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cl.ubb.entrenate.AgregarEjerciciosActivity;
import cl.ubb.entrenate.AgregarPerfil;
import cl.ubb.entrenate.AgregarUsuario;
import cl.ubb.entrenate.DetalleEjercicio;
import cl.ubb.entrenate.DetalleRutina;
import cl.ubb.entrenate.MainActivity;
import cl.ubb.entrenate.MainMenu;
import cl.ubb.entrenate.R;
import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.entidades.Rutina;

import static android.app.Activity.RESULT_OK;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    TextView txt_correo, txt_creacion, txt_rutinaActual;
    FirebaseFirestore bdd;
    ArrayList<String> correos, nombreRutinas;
    Date creacion;
    String s;
    Button btn_rutinaActual, btn_completar;
    ListView lista;
    ArrayAdapter adapter;
    ImageView imageView;
    private static int RESULT_LOAD_IMAGE = 1;
    Uri selectedImage, downloadUri;
    StorageReference storageReference;
    ConstraintLayout constraintLayout;
    FloatingActionButton fab;
    //RevealLayout mRevealLayout;

    ArrayList<Rutina> rutinas;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.perfil_fragment, container, false);
        bdd=FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
       // FloatingActionButton fab = root.findViewById(R.id.fab);

        correos= new ArrayList<>();
        rutinas= new ArrayList<>();
        nombreRutinas= new ArrayList<>();

        SharedPreferences prefs = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        txt_correo = (TextView) root.findViewById(R.id.txt_perfil_correo);
        txt_creacion = (TextView) root.findViewById(R.id.txt_perfil_dias);
        txt_rutinaActual = (TextView) root.findViewById(R.id.txt_perfil_rutinaActual);
        constraintLayout = (ConstraintLayout) root.findViewById(R.id.incompleto_perfil);
        btn_completar = (Button) root.findViewById(R.id.btn_perfil_completar);

        //mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
        //mRevealView = findViewById(R.id.reveal_view);

        //btn_rutinaActual = (Button) root.findViewById(R.id.btn_perfil_crear);
        //lista = root.findViewById(R.id.list_perfil_rutinas);
        //consultarRutina(correo);
        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });*/

        /*btn_completar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_completar.setClickable(false); // Avoid naughty guys clicking FAB again and again...
                int[] location = new int[2];
                btn_completar.getLocationOnScreen(location);
                location[0] += btn_completar.getWidth() / 2;
                location[1] += btn_completar.getHeight() / 2;

                final Intent intent = new Intent(getActivity(), AgregarPerfil.class);

                mRevealView.setVisibility(View.VISIBLE);
                mRevealLayout.setVisibility(View.VISIBLE);

                mRevealLayout.show(location[0], location[1]); // Expand from center of FAB. Actually, it just plays reveal animation.
                mFab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(0, R.anim.hold);
                    }
                }, 600); // 600 is default duration of reveal animation in RevealLayout
                mFab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFab.setClickable(true);
                        mRevealLayout.setVisibility(View.INVISIBLE);
                        mViewToReveal.setVisibility(View.INVISIBLE);
                    }
                }, 960); // Or some numbers larger than 600.
            }
        });*/

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
                                                       if(!documentSnapshots.getString("nombre").equalsIgnoreCase("")){
                                                           constraintLayout.setVisibility(View.GONE);
                                                       }
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

        /*bdd.collection("usuarios").document(correo).collection("rutinaActual")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                nombreRutinas.add(documentSnapshot.getString("rutinaActual"));
                                bdd.collection("rutinas")
                                        .whereEqualTo("nombre", documentSnapshot.getString("rutinaActual"))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                if (task1.isComplete()) {
                                                    for (QueryDocumentSnapshot documentSnapshots1 : task1.getResult()) {
                                                        rutinas.add(documentSnapshots1.toObject(Rutina.class));
                                                    }
                                                }else {
                                                    Log.e("Algo salió mal", "no sé donde");
                                                }

                                            }
                                        });
                                            //rutinas.add(document.get);
                                            for (String s : nombreRutinas) {
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
                                            }

                                    }


                            }
                            adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, nombreRutinas);
                            lista.setAdapter(adapter);

                        }

                });*/


        txt_correo.setText(correo);

        /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rutina selected = rutinas.get(position);
                String nombre = selected.getNombre();
                String descripcion = selected.getDescripcion();
                int vsx = selected.getVecesxsemana();
                int ide = selected.getId();
                startActivity(new Intent(getActivity(), DetalleRutina.class)
                        .putExtra("nombre", nombre)
                        .putExtra("descripcion", descripcion)
                        .putExtra("vxs", vsx)
                        .putExtra("id", ide));


            }
        });*/


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