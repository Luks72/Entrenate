package cl.ubb.entrenate.entidades;

import java.util.Date;

public class RutinaActual {

    private int id;
    private Date horario;
    private Date horarioMedicion;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaTermino;
    private int idRutina;
    private int idProgreso;
    private int idUsuario;
    private int idPreparadorFisico;

    public RutinaActual(int id, Date horario, Date horarioMedicion, String descripcion, Date fechaInicio, Date fechaTermino, int idRutina, int idProgreso, int idUsuario, int idPreparadorFisico) {
        this.id = id;
        this.horario = horario;
        this.horarioMedicion = horarioMedicion;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.idRutina = idRutina;
        this.idProgreso = idProgreso;
        this.idUsuario = idUsuario;
        this.idPreparadorFisico = idPreparadorFisico;
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

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public int getIdProgreso() {
        return idProgreso;
    }

    public void setIdProgreso(int idProgreso) {
        this.idProgreso = idProgreso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPreparadorFisico() {
        return idPreparadorFisico;
    }

    public void setIdPreparadorFisico(int idPreparadorFisico) {
        this.idPreparadorFisico = idPreparadorFisico;
    }

}
