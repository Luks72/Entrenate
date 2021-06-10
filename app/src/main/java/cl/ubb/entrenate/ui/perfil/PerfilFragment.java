package cl.ubb.entrenate.ui.perfil;

import androidx.cardview.widget.CardView;
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
import com.squareup.picasso.Picasso;

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
    TextView txt_correo, txt_creacion, txt_rutinaActual, txt_nombre, txt_telefono;
    FirebaseFirestore bdd;
    ArrayList<String> correos, nombreRutinas;
    Date creacion;
    String s, nombre, telefono, url;
    Button btn_rutinaActual, btn_completar;
    ListView lista;
    ArrayAdapter adapter;
    ImageView imageView;
    private static int RESULT_LOAD_IMAGE = 1;
    Uri selectedImage, downloadUri;
    StorageReference storageReference;
    ConstraintLayout constraintLayout;
    FloatingActionButton fab;
    ImageView imageView1;
    boolean valor;
    DocumentReference usuario;
    CardView cardView;

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
        correos= new ArrayList<>();
        rutinas= new ArrayList<>();
        nombreRutinas= new ArrayList<>();

        SharedPreferences prefs = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        String nombreUsuario = prefs.getString("nombre", null);
        usuario = bdd.collection("usuarios").document(correo);
        txt_correo = root.findViewById(R.id.txt_perfil_correo);
        constraintLayout = root.findViewById(R.id.incompleto_perfil);
        btn_completar = root.findViewById(R.id.btn_perfil_completar);
        imageView1 = root.findViewById(R.id.img_perfil_avatar);
        txt_nombre = root.findViewById(R.id.txt_perfil_nombre);
        txt_telefono = root.findViewById(R.id.txt_perfil_telefono);
        cardView = root.findViewById(R.id.card);

        if(!nombreUsuario.equals("")){
            constraintLayout.setVisibility(View.GONE);
            Log.e("el usuario", "si tiene nombre y el constrain no se muestra");
        }else{
            cardView.setVisibility(View.GONE);
            Log.e("el usuario", "no tiene nombre");
        }

        btn_completar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent= new Intent(getActivity(), AgregarPerfil.class);
                startActivity(miIntent);
            }
        });

        usuario.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    nombre = task.getResult().getString("nombre");
                    telefono = task.getResult().getString("telefono");
                    url = task.getResult().getString("url");
                }
                txt_nombre.setText(nombre);
                Picasso.get().load(url).into(imageView1);
                txt_telefono.setText(telefono);
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



}