package cl.ubb.entrenate.ui.ejercicios;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.LongFunction;

import cl.ubb.entrenate.AdminSQLiteAdminHelper;
import cl.ubb.entrenate.AgregarEjerciciosActivity;
import cl.ubb.entrenate.ClasificacionActivity;
import cl.ubb.entrenate.DetalleEjercicio;
import cl.ubb.entrenate.MainActivity;
import cl.ubb.entrenate.MainMenu;
import cl.ubb.entrenate.R;
import cl.ubb.entrenate.adaptadores.ImagenesAdaptador;
import cl.ubb.entrenate.entidades.Ejercicios;
import io.github.yavski.fabspeeddial.FabSpeedDial;

public class EjerciciosFragment extends Fragment implements Serializable{

    private static final String TAG = "MENSAJE";
    AdminSQLiteAdminHelper db;
    private GridView gridView;
    private EjerciciosViewModel ejerciciosViewModel;
    private ImagenesAdaptador adaptador;
    SwipeRefreshLayout refreshLayout;
    private ArrayList<Ejercicios> ejercicios;
    private TextView vacio;
    Spinner spinnerClasificacion;
    ArrayList<String> listEjercicios;
    ArrayAdapter adapterEjercicios;
    FabSpeedDial fabSpeedDial;
    SharedPreferences prefs;

    FirebaseFirestore bdd;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ejerciciosViewModel =  new ViewModelProvider(this).get(EjerciciosViewModel.class);

        View root = inflater.inflate(R.layout.fragment_ejercicios, container, false);

        db = new AdminSQLiteAdminHelper(getActivity(), "entrenate_bdd", null,1);

        bdd=FirebaseFirestore.getInstance();

        ejercicios= new ArrayList<>();
        gridView = (GridView) root.findViewById(R.id.grid);
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh);
        fabSpeedDial = root.findViewById(R.id.speed);
        listEjercicios = new ArrayList<>();
        vacio = (TextView) root.findViewById(R.id.vacio);

        prefs = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        if(!correo.contains("@preparador.cl")){
            fabSpeedDial.setVisibility(View.GONE);
        }

        if(!correo.contains("@preparador.cl")){
            String correoPreparador = prefs.getString("correoPreparador", null);
            correo = correoPreparador;
        }

        vacio.setVisibility(View.VISIBLE);
        gridView.setEmptyView(vacio);

        spinnerClasificacion = root.findViewById(R.id.spn_ejercicios);
        //rellenarSpinner();
        registerForContextMenu(gridView);

        rellenarspinner(correo);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ejercicios selected = ejercicios.get(position);
                String nombre = selected.getNombre();
                String descripcion = selected.getDescripcion();
                String url = selected.getUrl();
                int ide = selected.getId();
                String video = selected.getVideo();
                int id_clas=selected.getId_clasificacion();
                startActivity(new Intent(getActivity(), DetalleEjercicio.class)
                        .putExtra("nombre", nombre)
                        .putExtra("descripcion", descripcion)
                        .putExtra("url", url)
                        .putExtra("id", ide)
                        .putExtra("video", video)
                        .putExtra("idClas", id_clas));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager()
                        .beginTransaction()
                        .detach(EjerciciosFragment.this)
                        .attach(EjerciciosFragment.this)
                        .commit();
            }
        });

        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if(menuItem.getTitle().equals("Ejercicios")){
                    Intent miIntent= new Intent(getActivity(), AgregarEjerciciosActivity.class)
                            .putExtra("nombre", "");
                    startActivity(miIntent);
                }
                if(menuItem.getTitle().equals("Clasificación")){
                    Intent miIntent= new Intent(getActivity(), ClasificacionActivity.class);
                    startActivity(miIntent);
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });

        return root;
    }

    private void rellenarspinner(String correo) {

        bdd.collection("preparador").document(correo).collection("clasificacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   listEjercicios.clear();
                                                   listEjercicios.add("Seleccione una clasificación");
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()){
                                                       listEjercicios.add(documentSnapshots.getString("nombre"));
                                                   }
                                                   adapterEjercicios = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listEjercicios){
                                                       @Override
                                                       public boolean isEnabled(int position){
                                                           if (position == 0) {
                                                               return false;
                                                           } else {
                                                               return true;
                                                           }
                                                       }
                                                   };
                                                   spinnerClasificacion.setAdapter(adapterEjercicios);
                                                   spinnerClasificacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                       @Override
                                                       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                           if (position == 0) {
                                                               todos(correo);
                                                           } else {
                                                               rellenarGridejercicios(spinnerClasificacion.getSelectedItem().toString(), correo);
                                                           }

                                                       }

                                                       @Override
                                                       public void onNothingSelected(AdapterView<?> parent) {

                                                       }
                                                   });

                                               }
                                           }
                                       }

                );



    }


    public void rellenarGridejercicios(String selected, String correo){

        bdd.collection("preparador").document(correo).collection("clasificacion").document(selected).collection("ejercicios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   ejercicios.clear();
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()){
                                                       ejercicios.add(documentSnapshots.toObject(Ejercicios.class));
                                                   }

                                                   adaptador = new ImagenesAdaptador(getActivity(), ejercicios);
                                                   adaptador.notifyDataSetChanged();
                                                   gridView.setAdapter(adaptador);
                                               }
                                           }
                                       }

                );
    }
    public void todos(String correo){
        bdd.collection("preparador").document(correo).collection("clasificacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()) {
                                                       //Log.e("Nombre clasificacion", documentSnapshots.getString("nombre"));
                                                       bdd.collection("preparador").document(correo).collection("clasificacion").document(documentSnapshots.getString("nombre")).collection("ejercicios")
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                          @Override
                                                                                          public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                                                              if (task1.isSuccessful()){
                                                                                                  for (QueryDocumentSnapshot documentSnapshots1: task1.getResult()) {
                                                                                                      //Log.e("ejercicio", documentSnapshots1.getString("nombre"));
                                                                                                      ejercicios.add(documentSnapshots1.toObject(Ejercicios.class));
                                                                                                      //Log.e("agregado", "ejercicio agregado");

                                                                                                  }
                                                                                                  adaptador = new ImagenesAdaptador(getActivity(), ejercicios);
                                                                                                  //Log.e("gridview", spinnerClasificacion.getSelectedItem().toString());
                                                                                                  gridView.setAdapter(adaptador);
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