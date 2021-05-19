package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    TextView txt_agregarUsuario;
    EditText txt_correo, txt_contrasena;
    Button btn_ingresar;
    AdminSQLiteAdminHelper db;
    CheckBox checkBox;
    FirebaseAuth mAuth;
    androidx.constraintlayout.widget.ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);
        setTitle("Bienvenido a Entrénate");
        txt_agregarUsuario = (TextView) findViewById(R.id.txt_login_nuevoUsuario);
        btn_ingresar = (Button) findViewById(R.id.btn_login_ingresar);
        txt_correo = (EditText) findViewById(R.id.txt_login_correo);
        txt_contrasena = (EditText) findViewById(R.id.txt_login_contrasena);
        checkBox = (CheckBox) findViewById(R.id.check_login_recordar);
        layout = (androidx.constraintlayout.widget.ConstraintLayout) findViewById(R.id.loginLayout);

        mAuth = FirebaseAuth.getInstance();

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
                                    SharedPreferences prefs = getSharedPreferences("credenciales", MODE_PRIVATE);
                                    //SharedPreferences.Editor editor = prefs.edit();
                                    //editor.putString("correo", txt_correo.getText().toString());
                                    prefs.edit().putString("correo", txt_correo.getText().toString()).commit();
                                    prefs.edit().putString("contrasena", txt_contrasena.getText().toString()).commit();
                                    /*editor.putString("contra", txt_contrasena.getText().toString());
                                    editor.commit();*/
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