package cl.ubb.entrenate.entidades;

import java.util.Date;

public class Usuario {

    private int id;
    private String nombre;
    private String correo;

    public Usuario(int id, String contraseña, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo=correo;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo (){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo=correo;
    }
}
