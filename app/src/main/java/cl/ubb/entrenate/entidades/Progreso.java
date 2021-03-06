package cl.ubb.entrenate.entidades;

import java.util.Date;

public class Progreso {

    private int id;
    private Date fechayHora;
    private int medidaAbdomen;
    private int peso;

    public Progreso(int id, Date fechayHora, int medidaAbdomen, int peso) {
        this.id = id;
        this.fechayHora = fechayHora;
        this.medidaAbdomen = medidaAbdomen;
        this.peso = peso;
    }

    public Progreso() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(Date fechayHora) {
        this.fechayHora = fechayHora;
    }

    public int getMedidaAbdomen() {
        return medidaAbdomen;
    }

    public void setMedidaAbdomen(int medidaAbdomen) {
        this.medidaAbdomen = medidaAbdomen;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
