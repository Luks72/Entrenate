package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AgregarUsuario extends AppCompatActivity {

    private EditText txt_nombre, txt_direccion, txt_contrasena, txt_telefono, txt_confirmaContrasena, txt_correo, txt_fechaNacimiento;
    private Button btn_agregar;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    AdminSQLiteAdminHelper db;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);
        setTitle("Agregar nuevo usuario");
        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);

        txt_nombre = (EditText) findViewById(R.id.txt_agregarUsuario_nombre);
        txt_direccion = (EditText) findViewById(R.id.txt_agregarUsuario_direccion);
        txt_contrasena = (EditText) findViewById(R.id.txt_agregarUsuario_contraseña);
        txt_telefono = (EditText) findViewById(R.id.txt_agregarUsuario_telefono);
        txt_confirmaContrasena = (EditText) findViewById(R.id.txt_agregarUsuario_confirmarContraseña);
        txt_correo = (EditText) findViewById(R.id.txt_agregarUsuario_correo);
        txt_fechaNacimiento = (EditText) findViewById(R.id.txt_agregarUsuario_fechaNacimiento);
        btn_agregar = (Button) findViewById(R.id.btn_agregarUsuario_agregar);


        txt_fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int año = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AgregarUsuario.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        dia, mes, año);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txt_fechaNacimiento.setText(dayOfMonth+"/"+month+1+"/"+year);
            }
        };

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txt_nombre.getText().toString();
                String direccion= txt_direccion.getText().toString();
                String contrasena=txt_contrasena.getText().toString();
                String confContra=txt_confirmaContrasena.getText().toString();
                String telefono=txt_telefono.getText().toString();
                String correo=txt_correo.getText().toString();
                String fechaNac=txt_fechaNacimiento.getText().toString();
                if(contrasena.equals(confContra)){
                    db.agregar_usuarios(nombre, contrasena, telefono, direccion,fechaNac, correo);
                    Toast.makeText(AgregarUsuario.this, "Agregado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AgregarUsuario.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


}