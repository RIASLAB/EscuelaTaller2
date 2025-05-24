package Modelo;

public class Matricula {
    private Estudiante estudiante;
    private Curso curso;
    private float nota;

    public Matricula(Estudiante estudiante, Curso curso, float nota) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.nota = nota;
    }

    public Estudiante getEstudiante() { return estudiante; }
    public Curso getCurso() { return curso; }
    public float getNota() { return nota; }

    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public void setCurso(Curso curso) { this.curso = curso; }
    public void setNota(float nota) { this.nota = nota; }
}