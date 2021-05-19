package cl.ubb.entrenate.entidades;

import java.util.Date;

public class Rutina {

    private int id;
    private String nombre;
    private String descripcion;
    private int vecesxsemana;
    private int idEjercicio;

    public Rutina(int id, String nombre, String descripcion, int idEjercicio, int vecesxsemana) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idEjercicio = idEjercicio;
        this.vecesxsemana = vecesxsemana;
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

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVecesxsemana() {
        return vecesxsemana;
    }

    public void setVecesxsemana(int vecesxsemana) {
        this.vecesxsemana = vecesxsemana;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
