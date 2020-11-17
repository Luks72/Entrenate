package cl.ubb.entrenate.entidades;

import java.sql.Blob;

public class Ejercicios {
    private int id;
    private String nombre;
    private String descripción;
    /*private Blob foto;
    private Blob video;*/

    public Ejercicios(int id, String nombre, String descripción) {
        this.id = id;
        this.nombre = nombre;
        this.descripción = descripción;
        /*this.foto = foto;
        this.video = video;*/
    }

    public Ejercicios() {
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

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
/*
    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    public Blob getVideo() {
        return video;
    }

    public void setVideo(Blob video) {
        this.video = video;
    }

 */
}
