package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cl.ubb.entrenate.entidades.Clasificacion;
import cl.ubb.entrenate.ui.ejercicios.EjerciciosFragment;

public class AgregarEjerciciosActivity extends AppCompatActivity {

    private static final String TAG=  "Mensaje" ;
    AdminSQLiteAdminHelper db;

    Button agregar, imagen;
    EditText nombre, descripcion, video;

    ArrayList<String> listClasificacion, listEjercicios;
    ArrayAdapter adapterClasificacion, adapterEjercicios;

    ListView listViewClasificacion, listViewEjercicios;
    Spinner spinnerClasificacion;

    ImageView imgView;

    private static int RESULT_LOAD_IMAGE = 1;

    FirebaseFirestore bdd;
    StorageReference storageReference;
    Uri selectedImage;
    DocumentReference nuevoEjercicio;
    Uri downloadUri;
    Map<String, Object> data1 = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ejercicios);

        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);


        listClasificacion = new ArrayList<>();
        listEjercicios = new ArrayList<>();

        bdd=FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();



        imagen= findViewById(R.id.btn_agregarEjercicios_imagen);
        imgView= findViewById(R.id.img_agregarEjercicios_imagen);
        agregar= findViewById(R.id.btn_agregarEjercicios_agregar);
        nombre= findViewById(R.id.txt_agregarEjercicio_nombre);
        descripcion= findViewById(R.id.txt_agregarEjercicio_descripcion);
        video= findViewById(R.id.txt_agregarEjercicio_video);
        spinnerClasificacion = findViewById(R.id.spn_agregarEjercicios_clasificacion);
        spinnerClasificacion();
        setTitle("Agregar Ejercicios");

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

    }

    public void onClick (View view) {
        //Pasar imagen que está en el ImageView a arreglo de bytes para guardarlo en la BDD
        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
        Bitmap b = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 80, bos);
        byte[] img = bos.toByteArray();

        Object position =  spinnerClasificacion.getSelectedItem().toString();
        String name=nombre.getText().toString();
        String desc=descripcion.getText().toString();
        String vid=video.getText().toString();
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombre.getText().toString());
        data.put("descripcion", descripcion.getText().toString());
        data.put("NombreClasificacion", spinnerClasificacion.getSelectedItem().toString());
        //data.put("img", img.toString());
        data.put("video", video.getText().toString());
        data.put("url", "");

        /*Intent downloadIntent = new Intent(this, EjerciciosFragment.class)
                .putExtra("nombre spinner");*/

        nuevoEjercicio = bdd.collection("clasificacion").document(spinnerClasificacion.getSelectedItem().toString()).collection("ejercicios").document(nombre.getText().toString());

        nuevoEjercicio.set(data);
        nuevoEjercicio.update("url", downloadUri.toString());
        finish();

        //db.agregar_ejercicios(name, desc, position, img, vid);
        nombre.setText("");
        descripcion.setText("");
        video.setText("");
        imgView.setImageResource(android.R.color.transparent);
        //Hacer un Toast según el resultado, puedo poner uno que diga que no se pudo agregar el atributo
        //Funciona como exception
        Toast.makeText(AgregarEjerciciosActivity.this, "Ejercicio agregado", Toast.LENGTH_SHORT).show();
    }

    /*public void listaClasificacion() {
        Cursor cursor = db.prueba_innerJoin();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay Ejercicios", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                    listClasificacion.add(cursor.getString(0));
                    listClasificacion.add(cursor.getString(1));
            }
            adapterClasificacion = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listClasificacion);
            listViewClasificacion.setAdapter(adapterClasificacion);
        }
    }
     */

    public void spinnerClasificacion() {

        /*Cursor cursor = db.ver_clasificacion();
        listEjercicios.add("Seleccione tipo de ejercicio");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay ejercicios", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listEjercicios.add(cursor.getString(1));
            }

            adapterEjercicios = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listEjercicios){
                @Override
                public boolean isEnabled(int position){
                    if (position == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }
            };
            spinnerClasificacion.setAdapter(adapterEjercicios);

        }*/
        bdd.collection("clasificacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   listEjercicios.clear();
                                                   listEjercicios.add("Seleccione una clasificación");
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()){
                                                       listEjercicios.add(documentSnapshots.getString("nombre"));
                                                   }
                                                   adapterEjercicios = new ArrayAdapter<String>(AgregarEjerciciosActivity.this, android.R.layout.simple_list_item_1, listEjercicios){
                                                       @Override
                                                       public boolean isEnabled(int position){
                                                           if (position == 0) {
                                                               return false;
                                                           } else {
                                                               return true;
                                                           }
                                                       }
                                                   };
                                                   spinnerClasificacion.setAdapter(adapterEjercicios);
                                               }
                                           }
                                       }

                );
    }

    //Devolver la imagen para mostrarla en el Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            guardarImagen(selectedImage);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.img_agregarEjercicios_imagen);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    private void guardarImagen(Uri selectedImage) {
        StorageReference FilePath = storageReference.child("fotos ejercicios").child(selectedImage.getLastPathSegment());
        FilePath.putFile(selectedImage).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                //Devolvemos la url de descarga
                return FilePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    //Obtenemos la url descarga
                    downloadUri = task.getResult();
                    //Log.e(TAG, "onComplete: Success " );
                    //La ponemos en el database

                    data1.put("url", downloadUri.toString());
                    //bdd.collection("ejercicios").document(nombre.getText().toString()).set(data, SetOptions.merge());

                } else {
                    Toast.makeText(AgregarEjerciciosActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    //Log.e(TAG, task.getException().getMessage());
                }
            }
        });
    }
}