package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cl.ubb.entrenate.adaptadores.ImagenesAdaptador;
import cl.ubb.entrenate.entidades.Clasificacion;
import cl.ubb.entrenate.entidades.Ejercicios;
import cl.ubb.entrenate.ui.ejercicios.EjerciciosFragment;

public class AgregarEjerciciosActivity extends AppCompatActivity {

    private static final String TAG=  "Mensaje" ;
    AdminSQLiteAdminHelper db;

    Button agregar, cancelar;
    EditText nombre, descripcion, video;

    ArrayList<String> listClasificacion, listEjercicios;
    ArrayAdapter adapterClasificacion, adapterEjercicios;

    ListView listViewClasificacion, listViewEjercicios;
    Spinner spinnerClasificacion;
    boolean ImagenSeleccionada=false, imagencambiada=false;
    ImageView imgView;

    private static int RESULT_LOAD_IMAGE = 1;

    FirebaseFirestore bdd;
    StorageReference storageReference;
    Uri selectedImage;
    DocumentReference nuevoEjercicio;
    Uri downloadUri;
    Map<String, Object> data1 = new HashMap<>();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ejercicios);

        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);


        listClasificacion = new ArrayList<>();
        listEjercicios = new ArrayList<>();


        bdd=FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);

        imgView= findViewById(R.id.img_agregarEjercicios_imagen);
        agregar= findViewById(R.id.btn_agregarEjercicios_agregar);
        cancelar= findViewById(R.id.btn_agregarEjercicios_cancelar);
        nombre= findViewById(R.id.txt_agregarEjercicio_nombre);
        descripcion= findViewById(R.id.txt_agregarEjercicio_descripcion);
        video= findViewById(R.id.txt_agregarEjercicio_video);
        spinnerClasificacion = findViewById(R.id.spn_agregarEjercicios_clasificacion);
        AlertDialog alert = confirmar();
        spinnerClasificacion(correo);
        setTitle("Agregar Ejercicios");

        if(!getIntent().getExtras().get("nombre").equals("")){
            String nombre_ejercicio = (String) getIntent().getExtras().get("nombre");
            Log.e("intent", ""+getIntent().getExtras().get("nombre"));
            buscarEjercicio(nombre_ejercicio, correo);
        }

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(!getIntent().getExtras().get("nombre").equals("")){
                    alert.show();
                }else{
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombre.getText().toString().isEmpty()){
                    if(!descripcion.getText().toString().isEmpty()){
                        if(!video.getText().toString().isEmpty()){
                            if(spinnerClasificacion.getSelectedItemPosition()!=0){
                                if(ImagenSeleccionada==true){
                                    if(getIntent().getExtras().get("nombre").equals("")){
                                        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
                                        Bitmap b = drawable.getBitmap();
                                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                        b.compress(Bitmap.CompressFormat.PNG, 80, bos);

                                        Map<String, Object> data = new HashMap<>();
                                        data.put("nombre", nombre.getText().toString());
                                        data.put("descripcion", descripcion.getText().toString());
                                        data.put("NombreClasificacion", spinnerClasificacion.getSelectedItem().toString());
                                        data.put("video", video.getText().toString());
                                        data.put("url", "");

                                        nuevoEjercicio = bdd.collection("preparador").document(correo)
                                                .collection("clasificacion").document(spinnerClasificacion.getSelectedItem().toString())
                                                .collection("ejercicios").document(nombre.getText().toString());
                                        nuevoEjercicio.set(data);
                                        nuevoEjercicio.update("url", downloadUri.toString());
                                        finish();
                                        nombre.setText("");
                                        descripcion.setText("");
                                        video.setText("");
                                        imgView.setImageResource(android.R.color.transparent);
                                        Toast.makeText(AgregarEjerciciosActivity.this, "Ejercicio agregado", Toast.LENGTH_SHORT).show();
                                    }else{
                                        String nombre_ejercicio = (String) getIntent().getExtras().get("nombre");
                                        Map<String, Object> data = new HashMap<>();
                                        data.put("nombre", nombre.getText().toString());
                                        data.put("descripcion", descripcion.getText().toString());
                                        data.put("NombreClasificacion", spinnerClasificacion.getSelectedItem().toString());
                                        data.put("video", video.getText().toString());
                                        data.put("url", "");

                                        nuevoEjercicio = bdd.collection("preparador").document(correo)
                                                .collection("clasificacion").document(spinnerClasificacion.getSelectedItem().toString())
                                                .collection("ejercicios").document(nombre.getText().toString());
                                        nuevoEjercicio.set(data, SetOptions.merge());
                                        if(imagencambiada==true){
                                            nuevoEjercicio.update("url", downloadUri.toString());
                                        }else{
                                            nuevoEjercicio.update("url", url);
                                        }
                                        if(!nombre_ejercicio.equals(nombre.getText().toString())){
                                            borrarAnterior(nombre_ejercicio, correo, nombre.getText().toString(), spinnerClasificacion.getSelectedItem().toString());
                                        }
                                        finish();
                                        nombre.setText("");
                                        descripcion.setText("");
                                        video.setText("");
                                        imgView.setImageResource(android.R.color.transparent);
                                        Toast.makeText(AgregarEjerciciosActivity.this, "Ejercicio agregado", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(AgregarEjerciciosActivity.this, "Debe seleccionar una imagen", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(AgregarEjerciciosActivity.this, "Debe seleccionar una clasificación", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            video.setError("El campo no puede estar vacío");
                        }
                    }else{
                        descripcion.setError("El campo no puede estar vacío");
                    }
                }else{
                    nombre.setError("El campo no puede estar vacío");
                }
            }
        });
    }

    private void borrarAnterior(String nombre_ejercicio, String correo, String nombreNuevo, String clasificacion) {
        bdd.collection("preparador").document(correo)
                .collection("clasificacion").document(clasificacion)
                    .collection("ejercicios").document(nombre_ejercicio)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.e("documento", "eliminado");
                            }
                        });
    }

    private void buscarEjercicio(String nombre_ejercicio, String correo) {
        bdd.collection("preparador").document(correo).collection("clasificacion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   for (QueryDocumentSnapshot documentSnapshots: task.getResult()) {
                                                       bdd.collection("preparador").document(correo).collection("clasificacion").document(documentSnapshots.getString("nombre")).collection("ejercicios")
                                                               .whereEqualTo("nombre", nombre_ejercicio)
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                          @Override
                                                                                          public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                                                              if (task1.isSuccessful()){
                                                                                                  for (QueryDocumentSnapshot documentSnapshots1: task1.getResult()) {
                                                                                                      nombre.setText(documentSnapshots1.getString("nombre"));
                                                                                                      descripcion.setText(documentSnapshots1.getString("descripcion"));
                                                                                                      video.setText(documentSnapshots1.getString("video"));
                                                                                                      nombre.setText(documentSnapshots1.getString("nombre"));
                                                                                                      Picasso.get().load(documentSnapshots1.getString("url")).into(imgView);
                                                                                                      url=documentSnapshots1.getString("url");
                                                                                                      ImagenSeleccionada=true;
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

    public void spinnerClasificacion(String correo) {
        bdd.collection("preparador").document(correo).collection("clasificacion")
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            String correo = prefs.getString("correo", null);
            selectedImage = data.getData();
            guardarImagen(selectedImage, correo);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.img_agregarEjercicios_imagen);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            ImagenSeleccionada=true;
            imagencambiada=true;

        }
    }

    private void guardarImagen(Uri selectedImage, String correo) {
        StorageReference FilePath = storageReference.child(correo).child("fotos ejercicios").child(selectedImage.getLastPathSegment());
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
                    downloadUri = task.getResult();
                    data1.put("url", downloadUri.toString());
                } else {
                    Toast.makeText(AgregarEjerciciosActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private AlertDialog confirmar() {
        String nombre_ejercicio = (String) getIntent().getExtras().get("nombre");
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Seleccionar nueva imagen")
                .setMessage("La imagen anterior se borrará permanentemente")
                .setIcon(R.drawable.ic_baseline_delete_forever_24)

                .setPositiveButton("Cambiar foto", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
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