package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class EjerciciosActivity extends AppCompatActivity {

    AdminSQLiteAdminHelper db;

    Button agregar, imagen;
    EditText nombre, descripcion;

    ArrayList<String> listClasificacion, listEjercicios;
    ArrayAdapter adapterClasificacion, adapterEjercicios;

    ListView listViewClasificacion, listViewEjercicios;
    Spinner spinnerClasifiacacion;

    ImageView imgView;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);

        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);


        listClasificacion = new ArrayList<>();
        listEjercicios = new ArrayList<>();

        imagen= findViewById(R.id.btn_ejercicios_imagen);
        imgView= findViewById(R.id.img_ejercicios);

        agregar= findViewById(R.id.btn_ejercicios_agregar);
        nombre= findViewById(R.id.txt_ejercicios_nombre);
        descripcion= findViewById(R.id.txt_ejercicios_descripcion);
        //listViewClasificacion= findViewById(R.id.list_clasificacion_ejercicios);
        spinnerClasifiacacion = findViewById(R.id.spn_clasificacion_ejercicios);

        //listaClasificacion();
        spinnerClasificacion();

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
        b.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] img = bos.toByteArray();


        int position = spinnerClasifiacacion.getSelectedItemPosition();
        String name=nombre.getText().toString();
        String desc=descripcion.getText().toString();
        db.agregar_ejercicios(name, desc, position, img);
        nombre.setText("");
        descripcion.setText("");
        //Hacer un Toast según el resultado, puedo poner uno que diga que no se pudo agregar el atributo
        //Funciona como exception
        Toast.makeText(EjerciciosActivity.this, "Agregado", Toast.LENGTH_SHORT).show();
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

        Cursor cursor = db.ver_clasificacion();
        listEjercicios.add("Ejercicios");
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No hay ejercicios", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listEjercicios.add(cursor.getString(1));
            }

            adapterEjercicios = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listEjercicios);
            spinnerClasifiacacion.setAdapter(adapterEjercicios);

        }
    }

    //Devolver la imagen para mostrarla en el Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.img_ejercicios);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    public void onVerEjerciciosClick (View view){
        Intent miIntent=null;
        switch (view.getId()){
            case R.id.btn_ejercicios_verEjercicios:
                miIntent= new Intent(EjerciciosActivity.this, VerEjeciciosActivity.class);
                break;
        }
        if(miIntent!=null){
            startActivity(miIntent);
        }
    }


}