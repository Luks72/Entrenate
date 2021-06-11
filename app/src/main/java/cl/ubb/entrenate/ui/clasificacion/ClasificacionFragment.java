package cl.ubb.entrenate.ui.clasificacion;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import cl.ubb.entrenate.AgregarNuevoUsuarioActivity;
import cl.ubb.entrenate.ClasificacionActivity;
import cl.ubb.entrenate.R;
import cl.ubb.entrenate.adaptadores.ClasificacionAdaptador;
import cl.ubb.entrenate.entidades.Clasificacion;
import cl.ubb.entrenate.entidades.Usuario;

public class ClasificacionFragment extends Fragment {

    private ClasificacionViewModel mViewModel;
    ExtendedFloatingActionButton extendedFloatingActionButton;
    private GridView gridView;
    private ArrayList<Clasificacion> clasificacions;
    FirebaseFirestore bdd;
    private ClasificacionAdaptador adaptador;
    SharedPreferences prefs;
    private TextView vacio;

    public static ClasificacionFragment newInstance() {
        return new ClasificacionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.clasificacion_fragment, container, false);

        extendedFloatingActionButton = root.findViewById(R.id.fab_clasificacion);
        gridView = root.findViewById(R.id.grid_clasificacion);
        bdd=FirebaseFirestore.getInstance();
        clasificacions = new ArrayList<>();
        vacio = (TextView) root.findViewById(R.id.vacio_clasificacion);
        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent= new Intent(getActivity(), ClasificacionActivity.class);
                startActivity(miIntent);
            }
        });
        vacio.setVisibility(View.VISIBLE);
        gridView.setEmptyView(vacio);

        rellenargrid();
        return root;

    }

    private void rellenargrid() {
        prefs = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        bdd.collection("preparador").document(correo).collection("clasificacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   clasificacions.clear();
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()){
                                                       clasificacions.add(documentSnapshots.toObject(Clasificacion.class));
                                                   }
                                                   adaptador = new ClasificacionAdaptador(getActivity(), clasificacions);
                                                   adaptador.notifyDataSetChanged();
                                                   gridView.setAdapter(adaptador);
                                               }
                                           }
                                       }

                );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ClasificacionViewModel.class);
        // TODO: Use the ViewModel
    }

}