package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cl.ubb.entrenate.ui.perfil.PerfilFragment;

public class AgregarUsuario extends AppCompatActivity {

    private EditText txt_contrasena, txt_preparador, txt_confirmaContrasena, txt_correo;
    private Button btn_agregar, btn_buscar;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    AdminSQLiteAdminHelper db;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    FirebaseAuth mAuth;
    FirebaseFirestore bdd;
    DocumentReference preparador, usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);
        setTitle("Agregar nuevo usuario");
        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);

        mAuth = FirebaseAuth.getInstance();
        bdd=FirebaseFirestore.getInstance();

        //txt_nombre = (EditText) findViewById(R.id.txt_agregarUsuario_nombre);
        //txt_direccion = (EditText) findViewById(R.id.txt_agregarUsuario_direccion);
        txt_contrasena = (EditText) findViewById(R.id.txt_agregarUsuario_contraseña);
        //txt_telefono = (EditText) findViewById(R.id.txt_agregarUsuario_telefono);
        txt_confirmaContrasena = (EditText) findViewById(R.id.txt_agregarUsuario_confirmarContraseña);
        txt_correo = (EditText) findViewById(R.id.txt_agregarUsuario_correo);
        txt_preparador = (EditText) findViewById(R.id.txt_agregarUsuario_correoPreparador);
        //txt_fechaNacimiento = (EditText) findViewById(R.id.txt_agregarUsuario_fechaNacimiento);
        btn_agregar = (Button) findViewById(R.id.btn_agregarUsuario_agregar);
        btn_buscar = (Button) findViewById(R.id.btn_agregarUsuario_buscar);

        txt_correo.setEnabled(false);
        txt_contrasena.setEnabled(false);
        txt_confirmaContrasena.setEnabled(false);
        btn_agregar.setEnabled(false);


       btn_buscar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!txt_preparador.getText().toString().isEmpty()){
                   String correoPreparador = txt_preparador.getText().toString();
                   preparador = bdd.collection("preparador").document(correoPreparador);
                   preparador.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                           if(task.isSuccessful()){
                               DocumentSnapshot documentSnapshot = task.getResult();
                               if(documentSnapshot.exists()){
                                   txt_correo.setEnabled(true);
                                   txt_contrasena.setEnabled(true);
                                   txt_confirmaContrasena.setEnabled(true);
                                   btn_agregar.setEnabled(true);
                               } else {
                                   txt_preparador.setError("El correo no existe, comprueba que esté bien escrito ");
                               }
                           }
                       }
                   });
               }else{
                   txt_preparador.setError("El correo no puede estar vacío");
               }
           }
       });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txt_correo.getText().toString().isEmpty() && !txt_contrasena.getText().toString().isEmpty()) {
                    if (txt_contrasena.getText().toString().equals(txt_confirmaContrasena.getText().toString())) {
                        usuario = bdd.collection("preparador").document(txt_preparador.getText().toString()).collection("usuarios").document(txt_correo.getText().toString());
                        usuario.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    if(documentSnapshot.exists()) {
                                        crearUsuario(txt_correo.getText().toString(), txt_contrasena.getText().toString(), txt_preparador.getText().toString());
                                    }else{
                                        txt_correo.setError("Este correo no está asociado a este preparador físico");
                                    }
                                }
                            }
                        });
                    } else {
                        txt_contrasena.setError("Las contraseñas no coinciden");
                        txt_confirmaContrasena.setError("Las contraseñas no coinciden");
                    }
                } else {
                    txt_correo.setError("Debe rellenar los campos");
                    txt_contrasena.setError("Debe rellenar los campos");
                    txt_confirmaContrasena.setError("Debe rellenar los campos");
                }
            }
        });


    }

    private void crearUsuario(String correo, String contrasena, String correoPreparador) {
        mAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(AgregarUsuario.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("creacion", FieldValue.serverTimestamp());
                    bdd.collection("preparador").document(correoPreparador).collection("usuarios").document(correo).set(data, SetOptions.merge());
                    Toast.makeText(AgregarUsuario.this, "Agregado", Toast.LENGTH_SHORT).show();
                    SharedPreferences prefs = getSharedPreferences("credenciales", MODE_PRIVATE);
                    prefs.edit().putString("correoPreparador", correoPreparador).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("correo", txt_correo.getText().toString());
                    PerfilFragment fragobj = new PerfilFragment();
                    fragobj.setArguments(bundle);
                    finish();
                } else {
                    Toast.makeText(AgregarUsuario.this, "Algo salió mal, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                    txt_contrasena.setText("");
                    txt_confirmaContrasena.setText("");
                    txt_correo.setText("");
                }
            }
        });
    }


}