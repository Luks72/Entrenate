package cl.ubb.entrenate.entidades;

public class Rutina {

    private int id;
    private String nombre;
    private String descripcion;
    private int idEjercicio;

    public Rutina(int id, String nombre, String descripcion, int idEjercicio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idEjercicio = idEjercicio;
    }

    public Rutina() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

}
