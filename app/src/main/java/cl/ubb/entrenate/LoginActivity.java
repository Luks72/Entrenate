package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView txt_agregarUsuario;
    EditText txt_correo, txt_contrasena;
    Button btn_ingresar;
    AdminSQLiteAdminHelper db;
    CheckBox checkBox;


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
                String correo = txt_correo.getText().toString();
                String contra = txt_contrasena.getText().toString();
                if(db.revisar_usuario(correo, contra)){
                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainMenu.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "El usuario o la contraseña no coinciden", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}