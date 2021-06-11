package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import cl.ubb.entrenate.adaptadores.ImagenesAdaptador;
import cl.ubb.entrenate.entidades.Ejercicios;

public class LoginActivity extends AppCompatActivity {

    TextView txt_agregarUsuario;
    EditText txt_correo, txt_contrasena;
    Button btn_ingresar;
    AdminSQLiteAdminHelper db;
    FirebaseAuth mAuth;
    androidx.constraintlayout.widget.ConstraintLayout layout;
    FirebaseFirestore bdd;
    DocumentReference usuario;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);
        setTitle("Bienvenido a Entrénate");
        txt_agregarUsuario = findViewById(R.id.txt_login_nuevoUsuario);
        btn_ingresar = findViewById(R.id.btn_login_ingresar);
        txt_correo =findViewById(R.id.txt_login_correo);
        txt_contrasena =findViewById(R.id.txt_login_contrasena);
        layout =findViewById(R.id.loginLayout);
        bdd=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        prefs = getSharedPreferences("credenciales", MODE_PRIVATE);
        session();

        txt_agregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AgregarUsuario.class);
                startActivity(intent);
            }
        });

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_correo.getText().toString().isEmpty() && !txt_contrasena.getText().toString().isEmpty()){
                        mAuth.signInWithEmailAndPassword(txt_correo.getText().toString(), txt_contrasena.getText().toString()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                                    //SharedPreferences prefs = getSharedPreferences("credenciales", MODE_PRIVATE);
                                    prefs.edit().putString("correo", txt_correo.getText().toString()).commit();
                                    prefs.edit().putString("contrasena", txt_contrasena.getText().toString()).commit();
                                    buscarPreparador();
                                    /*usuario = bdd.collection("preparador").document(txt_correo.getText().toString());
                                    usuario.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(!task.getResult().getString("nombre").equals("")){
                                                prefs.edit().putString("nombre",task.getResult().getString("nombre")).commit();
                                                prefs.edit().putString("url",task.getResult().getString("url")).commit();
                                            }
                                        }
                                    });*/
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(LoginActivity.this, "El usuario o la contraseña no existen", Toast.LENGTH_SHORT).show();
                                    txt_contrasena.setText("");
                                    txt_correo.setText("");

                                    /*try {
                                        throw task.getException();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        Log.e("Excepcion", "esto salio mal", e);

                                    } catch (FirebaseNetworkException e) {
                                        Log.e("Excepcion", "esto salio mal", e);
                                    } catch (Exception e) {
                                        Log.e("Excepcion", "esto salio mal", e);
                                    }*/
                                }
                            }
                        });

                }else{
                    Toast.makeText(LoginActivity.this, "Los campos están vacíos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        }

    private void buscarPreparador() {
        prefs = getSharedPreferences("credenciales", MODE_PRIVATE);
        bdd.collection("preparador")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()) {
                                                       bdd.collection("preparador").document(documentSnapshots.getString("correo")).collection("usuarios")
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                          @Override
                                                                                          public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                                                              if (task1.isSuccessful()){
                                                                                                  for (QueryDocumentSnapshot documentSnapshots1: task1.getResult()) {
                                                                                                      if(documentSnapshots1.getString("correo").equals(txt_correo.getText().toString())){
                                                                                                          prefs.edit().putString("correoPreparador", documentSnapshots1.getString("correoPreparador")).commit();
                                                                                                          prefs.edit().putString("nombre", documentSnapshots1.getString("nombre")).commit();

                                                                                                      }
                                                                                                  }
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

    private void session() {
        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        String contra = prefs.getString("contrasena", null);
        //Log.i("correo", correo);
        if(correo !=null && contra!=null) {
            layout.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(LoginActivity.this, MainMenu.class);
            startActivity(intent);
        }

}
}