package cl.ubb.entrenate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.ui.ejercicios.EjerciciosFragment;

public class DetalleEjercicio extends AppCompatActivity implements Serializable {

    private ImageView img_foto;
    private TextView txt_nombre, txt_descripcion;
    //EditText nombreEjercicio, nombreClasificacion;
    private AdminSQLiteAdminHelper db;
    private Button btn_eliminar, btn_video, btn_editar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ejercicio);

        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);

        img_foto= findViewById(R.id.img_detalleEjercicio);
        txt_descripcion = findViewById(R.id.txt_detalleEjercicio_descripcion);
        btn_eliminar = findViewById(R.id.btn_detalleEjercicio_eliminar);
        btn_video = findViewById(R.id.btn_detalleEjercicio_video);
        btn_editar = findViewById(R.id.btn_detalleEjercicio_editar);
        AlertDialog alert = confirmar();


        String nombre_ejercicio = (String) getIntent().getExtras().get("nombre");
        String video_ejercicio = (String) getIntent().getExtras().get("video");
        String descripcion_ejercicio = (String) getIntent().getExtras().get("descripcion");
        byte[] imagen_ejercicio = (byte[]) getIntent().getExtras().get("imagen");
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(video_ejercicio)));
            }
        });
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private AlertDialog confirmar() {
        int id_ejercicio = (int) getIntent().getExtras().get("id");
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Eliminar ejercicio")
                .setMessage("¿Quieres eliminar este ejercicio")
                .setIcon(R.drawable.ic_baseline_delete_forever_24)

                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.eliminar_ejercicio(id_ejercicio);
                        Toast.makeText(DetalleEjercicio.this, "Eliminado", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }

                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }




}