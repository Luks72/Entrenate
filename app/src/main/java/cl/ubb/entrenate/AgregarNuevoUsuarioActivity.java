package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class AgregarNuevoUsuarioActivity extends AppCompatActivity {

    EditText txt_correo, txt_nombre;
    Button agregar;
    FirebaseFirestore bdd;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nuevo_usuario);
        bdd=FirebaseFirestore.getInstance();

        txt_correo = findViewById(R.id.itxt_agregarNuevoUsuario_correo);
        txt_nombre = findViewById(R.id.itxt_agregarNuevoUsuario_nombre);
        agregar = findViewById(R.id.btn_agregarNuevoUsuario_agregar);

        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_correo.getText().toString()!=null &&
                txt_nombre.getText().toString()!=null){
                    String correoUsuario= txt_correo.getText().toString();
                    String nombreUsuario= txt_nombre.getText().toString();
                    documentReference = bdd.collection("preparador").document(correo).collection("usuarios").document(correoUsuario);
                    Map<String, Object> data = new HashMap<>();
                    data.put("correo", correoUsuario);
                    data.put("nombre", nombreUsuario);
                    data.put("correoPreparador", correo);
                    documentReference.set(data, SetOptions.merge());
                }
            }
        });
    }
}