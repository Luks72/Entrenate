package cl.ubb.entrenate.utilidades;

public class Utilidades {

    // Clasificacion
    public static final String TABLA_CLASIFICACION="clasificacion";
    public static final String CAMPO_ID_CLASIFICACION="id";
    public static final String CAMPO_NOMBRE_CLASIFICACION="nombre";
    //public static final String CAMPO_IDEJERCICIOS_CLASIFICACION="idEjercicios";


    //Ejercicios
    public static final String TABLA_EJERCICIOS="ejercicios";
    public static final String CAMPO_ID_EJERCICIOS="id";
    public static final String CAMPO_NOMBRE_EJERCICIOS ="nombre";
    public static final String CAMPO_DESCRIPCION_EJERCICIOS ="descripcion";
    public static final String CAMPO_FOTO_EJERCICIOS ="foto";
    public static final String CAMPO_VIDEO_EJERCICIOS ="video";
    public static final String CAMPO_IDCLASIFICACION_EJERICIOS ="idClasificacion";


    //Preparador Fisico
    public static final String TABLA_PREPARADOR="preparador";
    public static final String CAMPO_ID_PREPARADOR = "id";
    public static final String CAMPO_NOMBRE_PREPARADOR="nombre";
    public static final String CAMPO_TELEFONO_PREPARADOR="telefono";
    public static final String CAMPO_CORREO_PREPARADOR="correo";
    public static final String CAMPO_IDRUTINA_PREPARADOR="idRutina";
    public static final String CAMPO_IDRUTINAACTUAL_PREPARADOR="idRutinaActual";

    //Progreso
    public static final String TABLA_PROGRESO="progreso";
    public static final String CAMPO_ID_PROGRESO="id";
    public static final String CAMPO_FECHAYHORA_PROGRESO="fechaYProgreso";
    public static final String CAMPO_MEDIDAABDOMEN_PROGRESO="medidaAbdomen";
    public static final String CAMPO_PESO_PROGRESO="peso";
    public static final String CAMPO_IDRUTINAACTUAL_PROGRESO="idRutinaActual";

    //Rutina
    public static final String TABLA_RUTINA="rutina";
    public static final String CAMPO_ID_RUTINA="id";
    public static final String CAMPO_NOMBRE_RUTINA="nombre";
    public static final String CAMPO_DESCRIPCION_RUTINA="descripcion";
    public static final String CAMPO_IDEJERCICIO_RUTINA="idEjercicio";

    //Rutina Actual
    public static final String TABLA_RUTINAACTUAL="rutinaActual";
    public static final String CAMPO_ID_RUTINAACTUAL="id";
    public static final String CAMPO_HORARIO_RUTINAACTUAL="horario";
    public static final String CAMPO_HORARIOMEDICION_RUTINAACTUAL="horarioMedicion";
    public static final String CAMPO_DESCRIPCION_RUTINAACTUAL="descripcion";
    public static final String CAMPO_FECHAINICIO_RUTINAACTUAL="fechaInicio";
    public static final String CAMPO_FECHATERMINO_RUTINAACTUAL="fechaTermino";
    public static final String CAMPO_IDRUTINA_RUTINAACTUAL="idRutina";
    public static final String CAMPO_IDPROGRESO_RUTINAACTUAL="idProgreso";
    public static final String CAMPO_IDUSUARIO_RUTINAACTUAL="idUsuario";
    public static final String CAMPO_IDPREPARADOR_RUTINAACTUAL="idPreparadorFisico";

    //Usuario
    public static final String TABLA_USUARIO="usuario";
    public static final String CAMPO_ID_USUARIO="id";
    public static final String CAMPO_CONTRASEÑA_USUARIO="contraseña";
    public static final String CAMPO_TIPOSUSCRIPCION_USUARIO="tipoSuscripcion";
    public static final String CAMPO_NOMBRE_USUARIO="nombre";
    public static final String CAMPO_EDAD_USUARIO="edad";
    public static final String CAMPO_TELEFONO_USUARIO="telefono";
    public static final String CAMPO_DIRECCION_USUARIO="direccion";
    public static final String CAMPO_FECHANACIMIENTO_USUARIO="fechaNacimiento";
    public static final String CAMPO_IDRUTINAACTUAL_USUARIO="idRutinaActual";



    public static final String CREAR_TABLA_CLASIFICACION ="CREATE TABLE "+TABLA_CLASIFICACION+" " +
            "("+CAMPO_ID_CLASIFICACION+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+CAMPO_NOMBRE_CLASIFICACION+" TEXT )";
            //""+CAMPO_IDEJERCICIOS_CLASIFICACION+" INTEGER, " +
            //"FOREIGN KEY ("+CAMPO_IDEJERCICIOS_CLASIFICACION+") REFERENCES "+TABLA_EJERCICIOS+"("+CAMPO_ID_EJERCICIOS+") " +

    public static final String CREAR_TABLA_EJERCICIOS ="CREATE TABLE "+TABLA_EJERCICIOS+"" +
            "("+CAMPO_ID_EJERCICIOS+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+CAMPO_NOMBRE_EJERCICIOS+" TEXT, " +
            ""+CAMPO_DESCRIPCION_EJERCICIOS+" TEXT, " +
            ""+CAMPO_FOTO_EJERCICIOS+" BLOB, " +
            ""+CAMPO_VIDEO_EJERCICIOS+" BLOB, " +
            ""+CAMPO_IDCLASIFICACION_EJERICIOS+" INTEGER, " +
            "FOREIGN KEY ("+CAMPO_IDCLASIFICACION_EJERICIOS+") REFERENCES "+TABLA_CLASIFICACION+"("+CAMPO_ID_CLASIFICACION+"))";

    public static final String CREAR_TABLA_PREPARADOR="CREATE TABLE "+TABLA_PREPARADOR+"" +
            "("+CAMPO_ID_PREPARADOR+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+CAMPO_NOMBRE_PREPARADOR+" TEXT, " +
            ""+CAMPO_TELEFONO_PREPARADOR+" TEXT, " +
            ""+CAMPO_CORREO_PREPARADOR+" TEXT, " +
            ""+CAMPO_IDRUTINA_PREPARADOR+"INTEGER, " +
            ""+CAMPO_IDRUTINAACTUAL_PREPARADOR+"INTEGER)";

    public static final String CREAR_TABLA_PROGRESO ="CREATE TABLE "+TABLA_PROGRESO+"" +
            "("+CAMPO_ID_PROGRESO+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+CAMPO_FECHAYHORA_PROGRESO+" NUMERIC, " +
            ""+CAMPO_MEDIDAABDOMEN_PROGRESO+" INTEGER, " +
            ""+CAMPO_PESO_PROGRESO+" INEGER, " +
            ""+CAMPO_IDRUTINAACTUAL_PROGRESO+" INTEGER, " +
            "FOREIGN KEY ("+CAMPO_IDRUTINAACTUAL_PROGRESO+") REFERENCES "+TABLA_RUTINAACTUAL+" ("+CAMPO_ID_RUTINAACTUAL+"))";

    public static final String CREAR_TABLA_RUTINA="CREATE TABLE "+TABLA_RUTINA+"" +
            "("+CAMPO_ID_RUTINA+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+CAMPO_NOMBRE_RUTINA+" TEXT, " +
            ""+CAMPO_DESCRIPCION_RUTINA+" TEXT, " +
            ""+CAMPO_IDEJERCICIO_RUTINA+" INTEGER, " +
            "FOREIGN KEY ("+CAMPO_IDEJERCICIO_RUTINA+") REFERENCES "+TABLA_EJERCICIOS+"("+CAMPO_ID_EJERCICIOS+"))";

    public static final String CREAR_TABLA_RUTINAACTUAL="CREATE TABLE "+TABLA_RUTINAACTUAL+"" +
            "("+CAMPO_ID_RUTINAACTUAL+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+CAMPO_HORARIO_RUTINAACTUAL+" NUMERIC, " +
            ""+CAMPO_HORARIOMEDICION_RUTINAACTUAL+" NUMERIC, " +
            ""+CAMPO_DESCRIPCION_RUTINAACTUAL+" TEXT, " +
            ""+CAMPO_FECHAINICIO_RUTINAACTUAL+" NUMERIC, " +
            ""+CAMPO_FECHATERMINO_RUTINAACTUAL+" NUMERIC, " +
            ""+CAMPO_IDRUTINA_RUTINAACTUAL+" INTEGER, " +
            ""+CAMPO_IDPROGRESO_RUTINAACTUAL+" INTEGER, " +
            ""+CAMPO_IDUSUARIO_RUTINAACTUAL+" INTEGER, " +
            ""+CAMPO_IDPREPARADOR_RUTINAACTUAL+" INTEGER, " +
            "FOREIGN KEY ("+CAMPO_IDRUTINA_RUTINAACTUAL+") REFERENCES "+TABLA_RUTINA+"("+CAMPO_ID_RUTINA+"), " +
            "FOREIGN KEY ("+CAMPO_IDPROGRESO_RUTINAACTUAL+") REFERENCES "+TABLA_PROGRESO+" ("+CAMPO_ID_PROGRESO+"), " +
            "FOREIGN KEY ("+CAMPO_IDUSUARIO_RUTINAACTUAL+") REFERENCES "+TABLA_USUARIO+" ("+CAMPO_ID_USUARIO+")," +
            "FOREIGN KEY ("+CAMPO_IDPREPARADOR_RUTINAACTUAL+") REFERENCES "+TABLA_PREPARADOR+" ("+CAMPO_ID_PREPARADOR+"))";

    public static final String CREAR_TABLA_USUARIO="CREATE TABLE "+TABLA_USUARIO+"" +
            "("+CAMPO_ID_USUARIO+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ""+CAMPO_CONTRASEÑA_USUARIO+" TEXT, " +
            ""+CAMPO_TIPOSUSCRIPCION_USUARIO+" INTEGER, " +
            ""+CAMPO_NOMBRE_USUARIO+" TEXT, " +
            ""+CAMPO_EDAD_USUARIO+" INTEGER, " +
            ""+CAMPO_TELEFONO_USUARIO+" TEXT, " +
            ""+CAMPO_DIRECCION_USUARIO+" TEXT, " +
            ""+CAMPO_FECHANACIMIENTO_USUARIO+" NUMERIC, " +
            ""+CAMPO_IDRUTINAACTUAL_USUARIO+" INTEGER, " +
            "FOREIGN KEY ("+CAMPO_IDRUTINAACTUAL_USUARIO+") REFERENCES "+TABLA_RUTINAACTUAL+" ("+CAMPO_ID_RUTINAACTUAL+"))";


}
