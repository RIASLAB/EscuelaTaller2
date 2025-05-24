package Modelo;

public class Curso {
    private int codigo;
    private String nombre;
    private Docente docente;

    public Curso(int codigo, String nombre, Docente docente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.docente = docente;
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Docente getDocente() { return docente; }

    public void setCodigo(int codigo) { this.codigo = codigo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDocente(Docente docente) { this.docente = docente; }

    @Override
    public String toString() {
        return nombre;
    }
}
