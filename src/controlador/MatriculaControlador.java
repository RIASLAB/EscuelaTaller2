package controlador;

import Modelo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaControlador {
    public List<Matricula> listarTodas() {
        List<Matricula> lista = new ArrayList<>();
        String sql = "SELECT e.cod_estudiante, e.nom_estudiante, c.cod_curso, c.nom_curso, d.cod_docente, d.nom_docente, m.nota_curso " +
                "FROM matricula m " +
                "JOIN estudiantes e ON m.cod_estudiante = e.cod_estudiante " +
                "JOIN cursos c ON m.cod_curso = c.cod_curso " +
                "JOIN docentes d ON c.cod_docente = d.cod_docente";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Estudiante est = new Estudiante(rs.getInt(1), rs.getString(2));
                Docente doc = new Docente(rs.getInt(5), rs.getString(6));
                Curso cur = new Curso(rs.getInt(3), rs.getString(4), doc);
                float nota = rs.getFloat(7);
                lista.add(new Matricula(est, cur, nota));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertar(Matricula m) {
        String sql = "INSERT INTO matricula (cod_estudiante, cod_curso, nota_curso) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, m.getEstudiante().getCodigo());
            ps.setInt(2, m.getCurso().getCodigo());
            ps.setFloat(3, m.getNota());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int codEst, int codCurso) {
        String sql = "DELETE FROM matricula WHERE cod_estudiante = ? AND cod_curso = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codEst);
            ps.setInt(2, codCurso);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
