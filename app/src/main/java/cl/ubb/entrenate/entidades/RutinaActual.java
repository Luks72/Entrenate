package cl.ubb.entrenate.entidades;

import java.util.Date;

public class RutinaActual {

    private int id;
    private Date horario;
    private Date horarioMedicion;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaTermino;

    public RutinaActual(int id, Date horario, Date horarioMedicion, String descripcion, Date fechaInicio, Date fechaTermino) {
        this.id = id;
        this.horario = horario;
        this.horarioMedicion = horarioMedicion;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
    }

    public RutinaActual() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public Date getHorarioMedicion() {
        return horarioMedicion;
    }

    public void setHorarioMedicion(Date horarioMedicion) {
        this.horarioMedicion = horarioMedicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }
}
