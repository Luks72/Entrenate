package cl.ubb.entrenate.entidades;

import java.sql.Blob;

public class Ejercicios {
    private int id;
    private String nombre;
    private String descripción;
    private byte[] imagen;
    private String video;

    public Ejercicios(int id, String nombre, String descripción, byte[] imagen, String video) {
        this.id = id;
        this.nombre = nombre;
        this.descripción = descripción;
        this.imagen = imagen;
        this.video = video;
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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String  video) {
        this.video = video;
    }


}
