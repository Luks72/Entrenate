package cl.ubb.entrenate.entidades;

public class Clasificacion {

    private int id;
    private String nombre;
    //private int idEjercicios;

    public Clasificacion(int id, String nombre, int idEjercicios) {
        this.id = id;
        this.nombre = nombre;
        //this.idEjercicios=idEjercicios;
    }

    public Clasificacion() {
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

    /*public int getIdEjercicios() {
        return idEjercicios;
    }

    public void setIdEjercicios(int idEjercicios) {
        this.idEjercicios = idEjercicios;
    }
    */

}
