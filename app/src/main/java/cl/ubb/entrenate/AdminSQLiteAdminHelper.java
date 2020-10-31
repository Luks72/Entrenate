package cl.ubb.entrenate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import cl.ubb.entrenate.utilidades.Utilidades;

import static cl.ubb.entrenate.utilidades.Utilidades.CAMPO_NOMBRE_CLASIFICACION;

public class AdminSQLiteAdminHelper extends SQLiteOpenHelper {

    public AdminSQLiteAdminHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_CLASIFICACION);
        db.execSQL(Utilidades.CREAR_TABLA_EJERCICIOS);
        db.execSQL(Utilidades.CREAR_TABLA_PREPARADOR);
        db.execSQL(Utilidades.CREAR_TABLA_PROGRESO);
        db.execSQL(Utilidades.CREAR_TABLA_RUTINA);
        db.execSQL(Utilidades.CREAR_TABLA_RUTINAACTUAL);
        db.execSQL(Utilidades.CREAR_TABLA_USUARIO);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clasificaion");
        db.execSQL("DROP TABLE IF EXISTS ejercicios");
        db.execSQL("DROP TABLE IF EXISTS prepador");
        db.execSQL("DROP TABLE IF EXISTS progreso");
        db.execSQL("DROP TABLE IF EXISTS rutina");
        db.execSQL("DROP TABLE IF EXISTS rutinaAdtual");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);
    }

    public boolean agregar_clasificacion (String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Utilidades.CAMPO_NOMBRE_CLASIFICACION, nombre);

        long result = db.insert(Utilidades.TABLA_CLASIFICACION, null, value);

        return result != -1 ;

    }

    public Cursor ver_clasificacion(){
        SQLiteDatabase db= this.getReadableDatabase();
        String query ="SELECT * FROM "+Utilidades.TABLA_CLASIFICACION;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;

    }



}
