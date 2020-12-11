package cl.ubb.entrenate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//Métodos comentados en el fondo
public class AdminSQLiteAdminHelper extends SQLiteOpenHelper {

    public AdminSQLiteAdminHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_CLASIFICACION);
        db.execSQL(CREAR_TABLA_EJERCICIOS);
        db.execSQL(CREAR_TABLA_PREPARADOR);
        db.execSQL(CREAR_TABLA_PROGRESO);
        db.execSQL(CREAR_TABLA_RUTINA);
        db.execSQL(CREAR_TABLA_RUTINAACTUAL);
        db.execSQL(CREAR_TABLA_USUARIO);
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
        value.put(CAMPO_NOMBRE_CLASIFICACION, nombre);

        long result = db.insert(TABLA_CLASIFICACION, null, value);

        return result != -1 ;
    }

    public Cursor ver_clasificacion(){
        SQLiteDatabase db= this.getReadableDatabase();
        String query ="SELECT * FROM "+TABLA_CLASIFICACION;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;

    }

    public Cursor ver_ejercicios(){
        SQLiteDatabase db= this.getReadableDatabase();
        String query ="SELECT * FROM "+TABLA_EJERCICIOS;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Boolean agregar_ejercicios (String nombre, String descripcion, int idClasificacion, byte [] foto){
        SQLiteDatabase db = this.getWritableDatabase();

         ContentValues value = new ContentValues();
         value.put(CAMPO_NOMBRE_EJERCICIOS, nombre);
         value.put(CAMPO_DESCRIPCION_EJERCICIOS, descripcion);
         value.put(CAMPO_IDCLASIFICACION_EJERICIOS, idClasificacion);
         value.put(CAMPO_FOTO_EJERCICIOS, foto);

         long result = db.insert(TABLA_EJERCICIOS, null, value);

         return result != -1;

    }

    public Cursor prueba_innerJoin(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query =
        "SELECT " +CAMPO_NOMBRE_EJERCICIOS+ ", " +CAMPO_NOMBRE_CLASIFICACION+
        " FROM " +TABLA_EJERCICIOS+
        " INNER JOIN " +TABLA_CLASIFICACION+
        " ON " +CAMPO_IDCLASIFICACION_EJERICIOS+ " = " +CAMPO_ID_CLASIFICACION ;


        Cursor cursor = db.rawQuery(query, null);
        return cursor;

    }

    public Cursor getImagen() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLA_EJERCICIOS, null);

        return cursor;
    }


        // Clasificacion
        public static final String TABLA_CLASIFICACION = "clasificacion";
        public static final String CAMPO_ID_CLASIFICACION = "id_Clasificacion";
        public static final String CAMPO_NOMBRE_CLASIFICACION = "nombre_Clasificacion";
        public static final String CAMPO_IDEJERCICIOS_CLASIFICACION = "idEjercicios_Clasificacion";


        //Ejercicios
        public static final String TABLA_EJERCICIOS = "ejercicios";
        public static final String CAMPO_ID_EJERCICIOS = "id_Ejercicios";
        public static final String CAMPO_NOMBRE_EJERCICIOS = "nombre_Ejercicios";
        public static final String CAMPO_DESCRIPCION_EJERCICIOS = "descripcion_Ejercicios";
        public static final String CAMPO_FOTO_EJERCICIOS = "foto_Ejercicios";
        public static final String CAMPO_VIDEO_EJERCICIOS = "video_Ejercicios";
        public static final String CAMPO_IDCLASIFICACION_EJERICIOS = "idClasificacion_Ejercicios";


        //Preparador Fisico
        public static final String TABLA_PREPARADOR = "preparador";
        public static final String CAMPO_ID_PREPARADOR = "id_Preparador";
        public static final String CAMPO_NOMBRE_PREPARADOR = "nombre_Preparador";
        public static final String CAMPO_TELEFONO_PREPARADOR = "telefono_Preparador";
        public static final String CAMPO_CORREO_PREPARADOR = "correo_Preparador";
        public static final String CAMPO_IDRUTINA_PREPARADOR = "idRutina_Preparador";
        public static final String CAMPO_IDRUTINAACTUAL_PREPARADOR = "idRutinaActual_Preparador";

        //Progreso
        public static final String TABLA_PROGRESO = "progreso";
        public static final String CAMPO_ID_PROGRESO = "id_Progreso";
        public static final String CAMPO_FECHAYHORA_PROGRESO = "fechaYProgreso_Progreso";
        public static final String CAMPO_MEDIDAABDOMEN_PROGRESO = "medidaAbdomen_Progreso";
        public static final String CAMPO_PESO_PROGRESO = "peso_Progreso";
        public static final String CAMPO_IDRUTINAACTUAL_PROGRESO = "idRutinaActual_Progreso";

        //Rutina
        public static final String TABLA_RUTINA = "rutina";
        public static final String CAMPO_ID_RUTINA = "id_Rutina";
        public static final String CAMPO_NOMBRE_RUTINA = "nombre_Rutina";
        public static final String CAMPO_DESCRIPCION_RUTINA = "descripcion_Rutina";
        public static final String CAMPO_IDEJERCICIO_RUTINA = "idEjercicio_Rutina";

        //Rutina Actual
        public static final String TABLA_RUTINAACTUAL = "rutinaActual";
        public static final String CAMPO_ID_RUTINAACTUAL = "id_RutinaActual";
        public static final String CAMPO_HORARIO_RUTINAACTUAL = "horario_RutinaActual";
        public static final String CAMPO_HORARIOMEDICION_RUTINAACTUAL = "horarioMedicion_RutinaActual";
        public static final String CAMPO_DESCRIPCION_RUTINAACTUAL = "descripcion_RutinaActual";
        public static final String CAMPO_FECHAINICIO_RUTINAACTUAL = "fechaInicio_RutinaActual";
        public static final String CAMPO_FECHATERMINO_RUTINAACTUAL = "fechaTermino_RutinaActual";
        public static final String CAMPO_IDRUTINA_RUTINAACTUAL = "idRutina_RutinaActual";
        public static final String CAMPO_IDPROGRESO_RUTINAACTUAL = "idProgreso_RutinaActual";
        public static final String CAMPO_IDUSUARIO_RUTINAACTUAL = "idUsuario_RutinaActual";
        public static final String CAMPO_IDPREPARADOR_RUTINAACTUAL = "idPreparadorFisico_RutinaActual";

        //Usuario
        public static final String TABLA_USUARIO = "usuario";
        public static final String CAMPO_ID_USUARIO = "id_Usuario";
        public static final String CAMPO_CONTRASEÑA_USUARIO = "contraseña_Usuario";
        public static final String CAMPO_TIPOSUSCRIPCION_USUARIO = "tipoSuscripcion_Usuario";
        public static final String CAMPO_NOMBRE_USUARIO = "nombre_Usuario";
        public static final String CAMPO_EDAD_USUARIO = "edad_Usuario";
        public static final String CAMPO_TELEFONO_USUARIO = "telefono_Usuario";
        public static final String CAMPO_DIRECCION_USUARIO = "direccion_Usuario";
        public static final String CAMPO_FECHANACIMIENTO_USUARIO = "fechaNacimiento_Usuario";
        public static final String CAMPO_IDRUTINAACTUAL_USUARIO = "idRutinaActual_Usuario";


        public static final String CREAR_TABLA_CLASIFICACION = "CREATE TABLE " + TABLA_CLASIFICACION + " " +
                "(" + CAMPO_ID_CLASIFICACION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + CAMPO_NOMBRE_CLASIFICACION + " TEXT, " +
                "" + CAMPO_IDEJERCICIOS_CLASIFICACION + " INTEGER, " +
                "FOREIGN KEY (" + CAMPO_IDEJERCICIOS_CLASIFICACION + ") " +
                "   REFERENCES " + TABLA_EJERCICIOS + "(" + CAMPO_ID_EJERCICIOS + ") " +
                "       ON DELETE CASCADE" +
                "       ON UPDATE CASCADE)";

        public static final String CREAR_TABLA_EJERCICIOS = "CREATE TABLE " + TABLA_EJERCICIOS + "" +
                "(" + CAMPO_ID_EJERCICIOS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + CAMPO_NOMBRE_EJERCICIOS + " TEXT, " +
                "" + CAMPO_DESCRIPCION_EJERCICIOS + " TEXT, " +
                "" + CAMPO_FOTO_EJERCICIOS + " BLOB, " +
                "" + CAMPO_VIDEO_EJERCICIOS + " BLOB, " +
                "" + CAMPO_IDCLASIFICACION_EJERICIOS + " INTEGER, " +
                "FOREIGN KEY (" + CAMPO_IDCLASIFICACION_EJERICIOS + ") " +
                "   REFERENCES " + TABLA_CLASIFICACION + "(" + CAMPO_ID_CLASIFICACION + ")" +
                "       ON DELETE CASCADE" +
                "       ON UPDATE CASCADE)";

        public static final String CREAR_TABLA_PREPARADOR = "CREATE TABLE " + TABLA_PREPARADOR + "" +
                "(" + CAMPO_ID_PREPARADOR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + CAMPO_NOMBRE_PREPARADOR + " TEXT, " +
                "" + CAMPO_TELEFONO_PREPARADOR + " TEXT, " +
                "" + CAMPO_CORREO_PREPARADOR + " TEXT, " +
                "" + CAMPO_IDRUTINA_PREPARADOR + " INTEGER, " +
                "" + CAMPO_IDRUTINAACTUAL_PREPARADOR + " INTEGER, " +
                "FOREIGN KEY (" + CAMPO_IDRUTINA_PREPARADOR + ") " +
                "   REFERENCES " + TABLA_RUTINA + " (" + CAMPO_ID_RUTINA + ") " +
                "       ON DELETE CASCADE" +
                "       ON UPDATE CASCADE, " +
                "FOREIGN KEY(" + CAMPO_IDRUTINAACTUAL_PREPARADOR + ") " +
                "   REFERENCES " + TABLA_RUTINAACTUAL + " (" + CAMPO_ID_RUTINAACTUAL + ") " +
                "       ON DELETE CASCADE" +
                "       ON UPDATE CASCADE)";

        public static final String CREAR_TABLA_PROGRESO = "CREATE TABLE " + TABLA_PROGRESO + "" +
                "(" + CAMPO_ID_PROGRESO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + CAMPO_FECHAYHORA_PROGRESO + " NUMERIC, " +
                "" + CAMPO_MEDIDAABDOMEN_PROGRESO + " INTEGER, " +
                "" + CAMPO_PESO_PROGRESO + " INEGER, " +
                "" + CAMPO_IDRUTINAACTUAL_PROGRESO + " INTEGER, " +
                "FOREIGN KEY (" + CAMPO_IDRUTINAACTUAL_PROGRESO + ") " +
                "   REFERENCES " + TABLA_RUTINAACTUAL + " (" + CAMPO_ID_RUTINAACTUAL + ") " +
                "       ON DELETE CASCADE " +
                "       ON UPDATE CASCADE)";

        public static final String CREAR_TABLA_RUTINA = "CREATE TABLE " + TABLA_RUTINA + "" +
                "(" + CAMPO_ID_RUTINA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + CAMPO_NOMBRE_RUTINA + " TEXT, " +
                "" + CAMPO_DESCRIPCION_RUTINA + " TEXT, " +
                "" + CAMPO_IDEJERCICIO_RUTINA + " INTEGER, " +
                "FOREIGN KEY (" + CAMPO_IDEJERCICIO_RUTINA + ") " +
                "   REFERENCES " + TABLA_EJERCICIOS + "(" + CAMPO_ID_EJERCICIOS + ") " +
                "       ON DELETE CASCADE " +
                "       ON UPDATE CASCADE)";

        public static final String CREAR_TABLA_RUTINAACTUAL = "CREATE TABLE " + TABLA_RUTINAACTUAL + "" +
                "(" + CAMPO_ID_RUTINAACTUAL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + CAMPO_HORARIO_RUTINAACTUAL + " NUMERIC, " +
                "" + CAMPO_HORARIOMEDICION_RUTINAACTUAL + " NUMERIC, " +
                "" + CAMPO_DESCRIPCION_RUTINAACTUAL + " TEXT, " +
                "" + CAMPO_FECHAINICIO_RUTINAACTUAL + " NUMERIC, " +
                "" + CAMPO_FECHATERMINO_RUTINAACTUAL + " NUMERIC, " +
                "" + CAMPO_IDRUTINA_RUTINAACTUAL + " INTEGER, " +
                "" + CAMPO_IDPROGRESO_RUTINAACTUAL + " INTEGER, " +
                "" + CAMPO_IDUSUARIO_RUTINAACTUAL + " INTEGER, " +
                "" + CAMPO_IDPREPARADOR_RUTINAACTUAL + " INTEGER, " +
                "FOREIGN KEY (" + CAMPO_IDRUTINA_RUTINAACTUAL + ") " +
                "   REFERENCES " + TABLA_RUTINA + "(" + CAMPO_ID_RUTINA + ") " +
                "       ON DELETE CASCADE " +
                "       ON UPDATE CASCADE, " +
                "FOREIGN KEY (" + CAMPO_IDPROGRESO_RUTINAACTUAL + ") " +
                "   REFERENCES " + TABLA_PROGRESO + " (" + CAMPO_ID_PROGRESO + ") " +
                "       ON DELETE CASCADE " +
                "       ON UPDATE CASCADE, " +
                "FOREIGN KEY (" + CAMPO_IDUSUARIO_RUTINAACTUAL + ") " +
                "   REFERENCES " + TABLA_USUARIO + " (" + CAMPO_ID_USUARIO + ") " +
                "       ON DELETE CASCADE " +
                "       ON UPDATE CASCADE," +
                "FOREIGN KEY (" + CAMPO_IDPREPARADOR_RUTINAACTUAL + ") " +
                "   REFERENCES " + TABLA_PREPARADOR + " (" + CAMPO_ID_PREPARADOR + ") " +
                "       ON DELETE CASCADE " +
                "       ON UPDATE CASCADE)";

        public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + "" +
                "(" + CAMPO_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "" + CAMPO_CONTRASEÑA_USUARIO + " TEXT, " +
                "" + CAMPO_TIPOSUSCRIPCION_USUARIO + " INTEGER, " +
                "" + CAMPO_NOMBRE_USUARIO + " TEXT, " +
                "" + CAMPO_EDAD_USUARIO + " INTEGER, " +
                "" + CAMPO_TELEFONO_USUARIO + " TEXT, " +
                "" + CAMPO_DIRECCION_USUARIO + " TEXT, " +
                "" + CAMPO_FECHANACIMIENTO_USUARIO + " NUMERIC, " +
                "" + CAMPO_IDRUTINAACTUAL_USUARIO + " INTEGER, " +
                "FOREIGN KEY (" + CAMPO_IDRUTINAACTUAL_USUARIO + ") " +
                "   REFERENCES " + TABLA_RUTINAACTUAL + " (" + CAMPO_ID_RUTINAACTUAL + ") " +
                "       ON DELETE CASCADE " +
                "       ON UPDATE CASCADE)";




}

/*    public Cursor ver_ejercicios(){
        SQLiteDatabase db= this.getReadableDatabase();
        String query ="SELECT * FROM "+TABLA_EJERCICIOS;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

 */

