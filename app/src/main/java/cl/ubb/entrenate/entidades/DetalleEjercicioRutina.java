package cl.ubb.entrenate.entidades;

public class DetalleEjercicioRutina {
    String nombreRutina;
    String nombre;
    String instrucciones;
    String repeticiones;
    String series;
    String descanso;

    public DetalleEjercicioRutina() {
    }

    public DetalleEjercicioRutina(String nombreRutina, String nombre, String instrucciones, String repeticiones, String series, String descanso) {
        this.nombreRutina = nombreRutina;
        this.nombre = nombre;
        this.instrucciones = instrucciones;
        this.repeticiones = repeticiones;
        this.series = series;
        this.descanso = descanso;
    }

    public String getNombreRutina() {
        return nombreRutina;
    }

    public void setNombreRutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDescanso() {
        return descanso;
    }

    public void setDescanso(String descanso) {
        this.descanso = descanso;
    }
}
