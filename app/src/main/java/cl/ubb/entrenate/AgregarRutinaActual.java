package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AgregarRutinaActual extends AppCompatActivity {

    FirebaseFirestore bdd;
    DocumentReference documentReference;
    TextView txt_inicio, txt_descripcion;
    Button btn_agregar;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rutina_actual);

        bdd=FirebaseFirestore.getInstance();
        txt_inicio = findViewById(R.id.date_agregarRutinaActual_inicio);
        txt_descripcion = findViewById(R.id.txt_agregarRutinaActual_descripcion);
        btn_agregar = findViewById(R.id.btn_rutinaActual_agregar);

        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        String nombre_rutina = (String) getIntent().getExtras().get("nombreRutina");
        String descripcion_rutina = (String) getIntent().getExtras().get("descripcion");
        setTitle(nombre_rutina);
        txt_descripcion.setText(descripcion_rutina);

        documentReference = bdd.collection("usuarios").document(correo).collection("rutinaActual").document(nombre_rutina);

        txt_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int año = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AgregarRutinaActual.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        año, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mes=month+1;
                txt_inicio.setText(dayOfMonth+"/"+mes+"/"+year);
            }
        };




        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_inicio.getText().toString()!=null){
                    String inicio = txt_inicio.getText().toString();
                    Map<String, Object> data = new HashMap<>();
                    data.put("fecha inicio", inicio);
                    documentReference.set(data, SetOptions.merge());
                    finish();
                }else{
                    Toast.makeText(AgregarRutinaActual.this, "Fecha actualizada", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}