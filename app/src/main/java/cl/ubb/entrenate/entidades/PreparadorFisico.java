package cl.ubb.entrenate.entidades;

public class PreparadorFisico {

    private int id;
    private String nombre;
    private String telefono;
    private String correo;
    private int idRutina;
    private int idRutinaActual;

    public PreparadorFisico(int id, String nombre, String telefono, String correo, int idRutina, int idRutinaActual) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.idRutina = idRutina;
        this.idRutinaActual = idRutinaActual;
    }

    public PreparadorFisico() {
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public int getIdRutinaActual() {
        return idRutinaActual;
    }

    public void setIdRutinaActual(int idRutinaActual) {
        this.idRutinaActual = idRutinaActual;
    }

}
