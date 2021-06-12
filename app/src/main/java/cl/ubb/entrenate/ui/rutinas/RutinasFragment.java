package cl.ubb.entrenate.ui.rutinas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cl.ubb.entrenate.AdminSQLiteAdminHelper;
import cl.ubb.entrenate.AgregarEjerciciosActivity;
import cl.ubb.entrenate.AgregarRutinaActivity;
import cl.ubb.entrenate.DetalleEjercicio;
import cl.ubb.entrenate.DetalleRutina;
import cl.ubb.entrenate.R;
import cl.ubb.entrenate.adaptadores.ImagenesAdaptador;
import cl.ubb.entrenate.adaptadores.RutinaAdaptador;
import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.entidades.Rutina;
import cl.ubb.entrenate.ui.ejercicios.EjerciciosFragment;

public class RutinasFragment extends Fragment {

    AdminSQLiteAdminHelper db;
    private RutinasViewModel rutinasViewModel;
    private GridView gridView;
    private TextView vacio;
    private ArrayList<Rutina> rutinas;
    private RutinaAdaptador adaptador;
    SwipeRefreshLayout refreshLayout;
    SharedPreferences prefs;
    FloatingActionButton floatingActionButton;

    FirebaseFirestore bdd;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rutinasViewModel = new ViewModelProvider(this).get(RutinasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rutinas, container, false);

        db = new AdminSQLiteAdminHelper(getActivity(), "entrenate_bdd", null,1);

        bdd= FirebaseFirestore.getInstance();

        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_rutina);
        floatingActionButton =  root.findViewById(R.id.floating_agregarRutina);
        prefs = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        rutinas= new ArrayList<>();
        gridView = (GridView) root.findViewById(R.id.grid_rutina);
        vacio = (TextView) root.findViewById(R.id.vacio_rutina);
        vacio.setVisibility(View.VISIBLE);
        gridView.setEmptyView(vacio);

        if(!correo.contains("@preparador.cl")){
            floatingActionButton.hide();
        }

        if(!correo.contains("@preparador.cl")){
            String correoPreparador = prefs.getString("correoPreparador", null);
            correo = correoPreparador;
        }


        rellenarGrid(correo);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rutina selected = rutinas.get(position);
                String nombre = selected.getNombre();
                String descripcion = selected.getDescripcion();
                int ide = selected.getId();
                int idEjercicio = selected.getIdEjercicio();
                startActivity(new Intent(getActivity(), DetalleRutina.class)
                        .putExtra("id", ide)
                        .putExtra("nombre", nombre)
                        .putExtra("descripcion", descripcion)
                        .putExtra("idEjercicio", idEjercicio));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager()
                        .beginTransaction()
                        .detach(RutinasFragment.this)
                        .attach(RutinasFragment.this)
                        .commit();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent= new Intent(getActivity(), AgregarRutinaActivity.class);
                startActivity(miIntent);
            }
        });

        return root;


    }

    private void rellenarGrid(String correo) {
        bdd.collection("preparador").document(correo).collection("rutinas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   rutinas.clear();
                                                   for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                                                       rutinas.add(documentSnapshots.toObject(Rutina.class));
                                                   }

                                                   adaptador = new RutinaAdaptador(getActivity(), rutinas);
                                                   adaptador.notifyDataSetChanged();
                                                   gridView.setAdapter(adaptador);
                                               }
                                           }
                                       }

                );
    }
}