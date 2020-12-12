package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import cl.ubb.entrenate.entidades.Ejercicios;

public class DetalleEjercicio extends AppCompatActivity implements Serializable {

    ImageView img_foto;
    TextView txt_nombre, txt_descripcion;
    //EditText nombreEjercicio, nombreClasificacion;
    AdminSQLiteAdminHelper db;
    Button btn_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ejercicio);

        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);

        img_foto= findViewById(R.id.img_detalleEjercicio);
        txt_descripcion = findViewById(R.id.txt_detalleEjercicio_descripcion);
        btn_eliminar = findViewById(R.id.btn_detalleEjercicio_eliminar);
        //nombreClasificacion= findViewById(R.id.txt_verEjercicios_clasificacionEjercicio);
       // nombreEjercicio= findViewById(R.id.txt_verEjercicios_nombreEjercicio);
        int id_ejercicio = (int) getIntent().getExtras().get("id");
        String nombre_ejercicio = (String) getIntent().getExtras().get("nombre");
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.eliminar_ejercicio(id_ejercicio);
                Toast.makeText(DetalleEjercicio.this, "Eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        setTitle(nombre_ejercicio);
        verImagen();

    }

    public void verImagen(){


        String descripcion_ejercicio = (String) getIntent().getExtras().get("descripcion");
        byte[] imagen_ejercicio = (byte[]) getIntent().getExtras().get("imagen");

        txt_descripcion.setText(descripcion_ejercicio);
        Bitmap bmp= BitmapFactory.decodeByteArray(imagen_ejercicio, 0 , imagen_ejercicio.length);
        img_foto.setImageBitmap(bmp);

    }




}