package cl.ubb.entrenate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick (View view){
        Intent miIntent=null;
        switch (view.getId()){
            case R.id.btn_clasificacion:
                miIntent= new Intent(MainActivity.this, ClasificacionActivity.class);
                break;
            case R.id.btn_ejercicios:
                miIntent= new Intent(MainActivity.this, EjerciciosActivity.class);
        }
        if(miIntent!=null){
            startActivity(miIntent);
        }
    }

}