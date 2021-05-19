package cl.ubb.entrenate.ui.home;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import cl.ubb.entrenate.AgregarUsuario;
import cl.ubb.entrenate.DetalleEjercicio;
import cl.ubb.entrenate.LoginActivity;
import cl.ubb.entrenate.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Se ha cerrado la sesión", Toast.LENGTH_SHORT).show();
                getActivity().getSharedPreferences("credenciales", 0).edit().clear().commit();
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });



        return root;
    }

    public void onClick(){
        
    }
}