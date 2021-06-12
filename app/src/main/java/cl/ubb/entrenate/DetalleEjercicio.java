package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.ui.ejercicios.EjerciciosFragment;

public class       DetalleEjercicio extends AppCompatActivity implements Serializable {

    private static final String TAG = "MENSAJE";
    private ImageView img_foto;
    private TextView txt_nombre, txt_descripcion, txtx_clasificacion;
    //EditText nombreEjercicio, nombreClasificacion;
    private AdminSQLiteAdminHelper db;
    private Button btn_eliminar, btn_video, btn_editar;
    FirebaseFirestore bdd;
    String nombre_ejercicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ejercicio);

        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);


        img_foto= findViewById(R.id.img_detalleEjercicio);
        txt_descripcion = findViewById(R.id.txt_detalleEjercicio_descripcion);
        txtx_clasificacion = findViewById(R.id.txt_detalleEjercicio_clasificacion);
        btn_eliminar = findViewById(R.id.btn_detalleEjercicio_eliminar);
        btn_video = findViewById(R.id.btn_detalleEjercicio_video);
        btn_editar = findViewById(R.id.btn_detalleEjercicio_editar);
        bdd= FirebaseFirestore.getInstance();
        AlertDialog alert = confirmar();


        nombre_ejercicio = (String) getIntent().getExtras().get("nombre");
        String video_ejercicio = (String) getIntent().getExtras().get("video");
        String descripcion_ejercicio = (String) getIntent().getExtras().get("descripcion");
        txt_descripcion.setText((String) getIntent().getExtras().get("descripcion"));
        String url_ejercicio = (String) getIntent().getExtras().get("url");
        Picasso.get().load(url_ejercicio).into(img_foto);
        setTitle(nombre_ejercicio);

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
                String nombre = nombre_ejercicio;
                startActivity(new Intent(DetalleEjercicio.this, AgregarEjerciciosActivity.class)
                        .putExtra("nombre", nombre));
            }
        });

        //rellenarDetalle();

    }

    public void rellenarDetalle(){
        String descripcion_ejercicio = (String) getIntent().getExtras().get("descripcion");
        //Log.e(TAG, descripcion_ejercicio);

    }

    private AlertDialog confirmar() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Eliminar ejercicio")
                .setMessage("El ejercicio se eliminará permanentemente")
                .setIcon(R.drawable.ic_baseline_delete_forever_24)

                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        eliminarEjercicio();
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

    private void eliminarEjercicio() {
        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);
        bdd.collection("preparador").document(correo).collection("clasificacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()) {
                                                       bdd.collection("preparador").document(correo)
                                                               .collection("clasificacion").document(documentSnapshots.getString("nombre"))
                                                                    .collection("ejercicios").document(nombre_ejercicio)
                                                                       .delete()
                                                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                           @Override
                                                                           public void onSuccess(Void aVoid) {
                                                                               Log.e("documento", "eliminado");
                                                                           }
                                                                       });
                                                   }
                                               }
                                           }
                                       }
                );
    }
}