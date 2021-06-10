package cl.ubb.entrenate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AgregarPerfil extends AppCompatActivity {
    FirebaseFirestore bdd;
    StorageReference storageReference;
    ImageView imageView;
    EditText nombre, telefono;
    Button agregar;
    DocumentReference usuario;
    private static int RESULT_LOAD_IMAGE = 1;

    Uri selectedImage, downloadUri;

    Map<String, Object> data1 = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_perfil);

        bdd=FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        SharedPreferences prefs = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String correo = prefs.getString("correo", null);

        imageView = findViewById(R.id.img_agregarPerfil_avatar);
        nombre = findViewById(R.id.itxt_agregarPerfil_nombre);
        telefono = findViewById(R.id.itxt_agregarPerfil_telefono);
        agregar = findViewById(R.id.btn_agregarPerfil_agregar);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(telefono.getText().toString().contains("+569")){
                    if(!telefono.getText().toString().isEmpty() &&
                    !nombre.getText().toString().isEmpty() &&
                    imageView!=null){
                        Map<String, Object> data = new HashMap<>();
                        data.put("nombre", nombre.getText().toString());
                        data.put("telefono", telefono.getText().toString());
                        data.put("url", "");
                        usuario = bdd.collection("usuarios").document(correo);
                        usuario.set(data, SetOptions.merge());
                        usuario.update("url", downloadUri.toString());
                        prefs.edit().putString("nombre", nombre.getText().toString()).commit();
                        prefs.edit().putString("url", downloadUri.toString()).commit();
                        Toast.makeText(AgregarPerfil.this, "Perfil agregado", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        telefono.setError("El campo no puede estar vacío");
                        nombre.setError("El campo no puede estar vacío");
                    }

                }else{
                    telefono.setError("El telefono debe comenzar con +569");
                }


            }
        });




    }
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
            ImageView imageView = (ImageView) findViewById(R.id.img_agregarPerfil_avatar);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    private void guardarImagen(Uri selectedImage) {
        StorageReference FilePath = storageReference.child("fotos usuarios").child(selectedImage.getLastPathSegment());
        FilePath.putFile(selectedImage).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return FilePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                    data1.put("url", downloadUri.toString());
                } else {
                    Toast.makeText(AgregarPerfil.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}