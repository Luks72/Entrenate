package cl.ubb.entrenate.entidades;

import java.sql.Blob;

public class Ejercicios {
    private int id;
    private String nombre;
    private String descripcion;
    private String video;
    private int id_clasificacion;
    private String url;
    private String NombreClasificacion;

    public Ejercicios(int id, String nombre, String descripcion, String video, int id_clasificacion, String url, String NombreClasificacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        //this.imagen = imagen;
        this.video = video;
        this.id_clasificacion=id_clasificacion;
        this.url=url;
        this.NombreClasificacion=NombreClasificacion;
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

    public int getId_clasificacion() {
        return id_clasificacion;
    }
    public void setId_clasificacion(int id_clasificacion) {
        this.id_clasificacion = id_clasificacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripción(String descripción) {
        this.descripcion = descripción;
    }

   /* public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String  video) {
        this.video = video;
    }

    public String getNombreClasificacion() {
        return NombreClasificacion;
    }

    public void setNombreClasificacion(String nombreClasificacion) {
        NombreClasificacion = nombreClasificacion;
    }
}
