package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class VerEjeciciosActivity extends AppCompatActivity {

    ImageView imgView;
    //EditText nombreEjercicio, nombreClasificacion;
    AdminSQLiteAdminHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejecicios);

        db = new AdminSQLiteAdminHelper(this, "entrenate_bdd", null,1);

        imgView= findViewById(R.id.img_verEjercicios_imagen);

        //nombreClasificacion= findViewById(R.id.txt_verEjercicios_clasificacionEjercicio);
       // nombreEjercicio= findViewById(R.id.txt_verEjercicios_nombreEjercicio);

        verImagen();

    }

    public void verImagen(){
        Cursor cursor = db.getImagen();
        if(cursor.moveToNext())
        {
            byte[] image = cursor.getBlob(3);
            Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
            imgView.setImageBitmap(bmp);
            Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show();
        }

    }




}